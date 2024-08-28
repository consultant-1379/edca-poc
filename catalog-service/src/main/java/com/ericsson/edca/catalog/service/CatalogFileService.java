package com.ericsson.edca.catalog.service;

import com.ericsson.edca.catalog.model.Catalog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Service
public class CatalogFileService {

    public Catalog processCatalog(Catalog catalog, boolean isUpdationAllowed) throws URISyntaxException {

        String datatype = catalog.getDataType();
        String dataNotification = catalog.getDataNotification();
        if (datatype != null && dataNotification != null) {
            try {
                File directory = new File("./catalog");
                boolean result = !directory.isDirectory() ? directory.mkdir() : Boolean.TRUE;
                if (!result) return null;

                File file = new File(directory.getAbsolutePath() + File.separator + datatype + "_" + dataNotification + ".json");
                if (file.exists() && !isUpdationAllowed) { // POST
                    // file.createNewFile();
                    return null;

                } else if (file.exists() && isUpdationAllowed) { // PUT
                    ObjectMapper mapper = new ObjectMapper();
                    String catalogJson = mapper.writeValueAsString(catalog);
                    PrintWriter pw = new PrintWriter(file);
                    pw.println(catalogJson);
                    pw.close();
                } else if (!file.exists() && isUpdationAllowed) { // PUT
                    return null;
                } else { // POST
                    file.createNewFile();
                    ObjectMapper mapper = new ObjectMapper();
                    String catalogJson = mapper.writeValueAsString(catalog);
                    PrintWriter pw = new PrintWriter(file);
                    pw.println(catalogJson);
                    pw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return catalog;

        } else
            return null;
    }

    private String joinCatalogData(String[] stringArr) {
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < stringArr.length; i++) {
            joiner.add(stringArr[i]);
        }
        String dataSubType = joiner.toString();

        return dataSubType;

    }

    public List<Catalog> fetchCatalogs() throws Exception {
        Catalog catalog = new Catalog();
        List<Catalog> catalogs = new ArrayList<>();
        File file = new File("DataType_DataContext.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        int count = 0;
        String st;
        while ((st = br.readLine()) != null) {
            count++;
            if (count == 0) {
                catalog.setDataType(st);
            } else if (count == 1) {
                catalog.setDataNotification(st);
            } else if (count == 2) {
                catalog.setSpecRef(st);
            } else if (count == 3) {
                catalog.setDataSubType(st.split(","));
            } else if (count == 4) {
//				catalog.setEncodingType(st);
            } else if (count == 5) {
                catalog.setSourceType(st.split(","));
            }
        }
        br.close();
        catalogs.add(catalog);
        return catalogs;
    }


    public Optional<Catalog> getFileCatalogById(String datatype, String dataNotification) throws Exception {
        File directory = new File("./catalog");
        File file = new File(directory.getAbsolutePath() + File.separator + datatype + "_" + dataNotification + ".json");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String finalStr;
        String st;
        StringBuffer buffer = new StringBuffer();
        while ((st = br.readLine()) != null) {
            buffer.append(st);
        }
        br.close();
        finalStr = buffer.toString();
        if (finalStr == null || finalStr == "")
            return null;
        ObjectMapper mapper = new ObjectMapper();
        Catalog catalog = mapper.readValue(finalStr, Catalog.class);
        Optional<Catalog> catalogList = Optional.of(catalog);
        return catalogList;
    }

    public List<Catalog> getFileCatalogs() throws Exception {
        List<Catalog> catalogs = new ArrayList<>();
        File directory = new File("./catalog");
        if (directory.exists() && directory.isDirectory()) {
            File[] fileList = directory.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (File file2 : fileList) {
                    BufferedReader br = new BufferedReader(new FileReader(file2));
                    String finalStr;
                    String st;
                    StringBuffer buffer = new StringBuffer();
                    while ((st = br.readLine()) != null) {
                        buffer.append(st);
                    }
                    br.close();
                    finalStr = buffer.toString();
                    if (finalStr == null || finalStr == "")
                        return null;
                    ObjectMapper mapper = new ObjectMapper();
                    Catalog catalog = mapper.readValue(finalStr, Catalog.class);
                    catalogs.add(catalog);
                }
            }
        }

        return catalogs;
    }
}
