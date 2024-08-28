package com.ericsson.edca.catalog.service;

import com.ericsson.edca.catalog.model.Catalog;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.openmbean.InvalidKeyException;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class MinioService {

    @Value("${catalog.url}")
    private String URL;
    @Value("${catalog.port}")
    private int PORT;
    @Value("${catalog.accesskey}")
    private String ACCESSKEY;
    @Value("${catalog.secertkey}")
    private String SECERTKEY;
    @Value("${catalog.file_download_dir}")
    private String FILE_DOWNLOAD_DIR;
    @Value("${catalog.file_upload_dir}")
    private String FILE_UPLOAD_DIR;
    @Value("${catalog.bucket_name}")
    private String BUCKET_NAME;


    public Catalog processCatalog(Catalog catalog, boolean isUpdationAllowed) throws Exception {

        String datatype = catalog.getDataType();
        String dataNotification = catalog.getDataNotification();
        if (datatype != null && dataNotification != null) {
            File directory = new File(FILE_UPLOAD_DIR);
            File file = new File(
                    directory.getAbsolutePath() + File.separator + datatype + "_" + dataNotification + ".json");
            try {
                boolean result = !directory.isDirectory() ? directory.mkdir() : Boolean.TRUE;
                if (!result)
                    return null;


                if (file.exists() && !isUpdationAllowed) { // POST
                    // file.createNewFile();
                    return null;

                } else if (isUpdationAllowed) { // PUT
                    ObjectMapper mapper = new ObjectMapper();
                    downloadObject(BUCKET_NAME, datatype + "_" + dataNotification + ".json", file.getPath());
                    if (file.exists()) {
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
                        ObjectMapper mapperRead = new ObjectMapper();
                        Catalog catalogRead = mapperRead.readValue(finalStr, Catalog.class);
                        String minIoSourceType = Arrays.toString(catalogRead.getSourceType());
                        String inputReqSourceType = Arrays.toString(catalog.getSourceType());
                        String minIoSubType = Arrays.toString(catalogRead.getDataSubType());
                        String inputReqSubType = Arrays.toString(catalog.getDataSubType());

                        if ((!StringUtils.equals(minIoSourceType, inputReqSourceType))
                                || (!StringUtils.equals(minIoSubType, inputReqSubType))
                                && StringUtils.equals(catalogRead.getSpecRef(), catalog.getSpecRef())
                                && StringUtils.equals(catalogRead.getAddress(), catalog.getAddress())
                                && StringUtils.equals(catalogRead.getEncoding().name(), catalog.getEncoding().name())) {
                            String catalogJson = mapper.writeValueAsString(catalog);
                            PrintWriter pw = new PrintWriter(file);
                            pw.println(catalogJson);
                            pw.close();
                            uploadObject(BUCKET_NAME, file.getName(), file.getPath());
                        } else
                            throw new RuntimeException("Updation on the changed attributes are not allowed.");
                    }

                } else if (!file.exists() && isUpdationAllowed) { // PUT
                    return null;
                } else { // POST
                    file.createNewFile();
                    ObjectMapper mapper = new ObjectMapper();
                    String catalogJson = mapper.writeValueAsString(catalog);
                    PrintWriter pw = new PrintWriter(file);
                    pw.println(catalogJson);
                    pw.close();
                    uploadObject(BUCKET_NAME, file.getName(), file.getPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                purgeDir(directory);
            }

            return catalog;

        } else
            return null;
    }

    public Optional<Catalog> getFileCatalogById(String datatype, String dataNotification) throws Exception {
        // File file = new File("Catalog\\"+datatype +"_"+ dataNotification);
        File directory = new File(FILE_DOWNLOAD_DIR);
        File file = new File(
                directory.getAbsolutePath() + File.separator + datatype + "_" + dataNotification + ".json");
        if (!directory.isDirectory())
            directory.mkdir();
        downloadObject(BUCKET_NAME, datatype + "_" + dataNotification + ".json", file.getPath());
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
        purgeDir(directory);
        return catalogList;
    }

    public List<Catalog> getFileCatalogs() throws Exception {
        List<Catalog> catalogs = new ArrayList<>();
        MinioClient minioClient = MinioClient.builder().endpoint("http://" + URL).credentials(ACCESSKEY, SECERTKEY).build();
        File directory = new File(FILE_DOWNLOAD_DIR);
        File file = new File(
                directory.getAbsolutePath() + File.separator + "datatype" + "_" + "dataNotification" + ".json");

        if (!directory.isDirectory())
            directory.mkdir();//yes


        Iterable<Result<Item>> minIoObjects = minioClient.listObjects(BUCKET_NAME);
        minIoObjects.forEach((Result<Item> result) -> {
            try {
                String objectName = result.get().objectName();
                if (objectName.endsWith(".json")) {
                    minioClient.downloadObject(DownloadObjectArgs.builder().bucket(BUCKET_NAME).filename(directory.getPath() + File.separator + objectName)
                            .object(objectName).build());
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

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
        //New changes
        ObjectMapper mappers = new ObjectMapper();
        String catalogsJson = mappers.writeValueAsString(catalogs);
        PrintWriter pw = new PrintWriter(new File(FILE_DOWNLOAD_DIR + File.separator + "catalogs" + ".json"));
        pw.println(catalogsJson);
        pw.close();
        purgeDir(directory);
        return catalogs;
    }


    public void uploadObject(String bucketname, String objectName, String path)
            throws InvalidKeyException, IllegalArgumentException,
            NoSuchAlgorithmException, Exception {

        try {
            MinioClient minioClient = MinioClient.builder().endpoint(URL, PORT,
                    false).credentials(ACCESSKEY, SECERTKEY).build();


            // check Bucket
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketname).build());
            if (isExist) {
                minioClient.uploadObject(
                        UploadObjectArgs.builder().bucket(bucketname).object(objectName).filename(path).build());
                System.out.println("================================================================");
                System.out.println(objectName + " is uploaded to  " + bucketname + " Successfully");
                System.out.println();
            } else {

                System.out.println("Bucket: " + bucketname + "not exist");
            }

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }

    }

    public void downloadObject(String bucketname, String objectName, String path)
            throws InvalidKeyException, IllegalArgumentException,
            NoSuchAlgorithmException, Exception {


        try {

            MinioClient minioClient = MinioClient.builder().endpoint(URL, PORT,
                    false).credentials(ACCESSKEY, SECERTKEY).build();


            // check bucket is exist or not
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketname).build());
            if (isExist) {
                minioClient.downloadObject(
                        DownloadObjectArgs.builder().bucket(bucketname).object(objectName).filename(path).build());


                System.out.println("===============================");
                System.out.println(objectName + " File is Successfully Downloaded");

            } else {
                System.out.println(bucketname + " Bucket is not exist");
            }

        } catch (MinioException e) {
            System.out.println("Error occurred in file downloading: " + e.getMessage());
            System.out.println(e);
        }
    }

    private void purgeDir(File file) throws IOException {
        FileUtils.deleteDirectory(file);

    }
}