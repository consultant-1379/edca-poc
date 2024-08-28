package com.ericsson.edca.catalog.service;

import com.ericsson.edca.catalog.controller.CatalogFileController;
import com.ericsson.edca.catalog.model.Catalog;
import com.ericsson.edca.catalog.model.Encoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CatalogFileController.class)
//controller
class CatalogFileServiceTest {

    private static final String BASE_URL = "/api/v1/edca";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CatalogFileService catalogFileService;

    private List<Catalog> catalogList;

    @BeforeEach
    void fileCataLogList() {
        this.catalogList = new ArrayList<Catalog>();
        this.catalogList.add(new Catalog("PM Traffic Events", "topic1",
                new String[]{"PM_BSC_CER", "PM_BSC_RIR", "PM_BSC_BAR", "PM_BSC_MRR", "PM_MTR_RAW", "PM_MTR"},
                new String[]{"eNB", "gNB"}, Encoding.JSON, "", ""));

        this.catalogList.add(new Catalog("Log", "topic1",
                new String[]{"PM_BSC_PERFORMANCE_EVENT_STATISTICS", "PM_BSC_PERFORMANCE_CTRL", "PM_RTT"},
                new String[]{"gNB"}, Encoding.CSV, "", ""));

        this.catalogList.add(new Catalog("PM Traffic Events", "topic2",
                new String[]{"PM_GPEH", "PM_STATISTICAL_RAW", "PM_BSC_CTR_RAW", "PM_BSC_CER_RAW"},
                new String[]{"eNB"}, Encoding.EXCEL, "", ""));
    }

    @Test
    void listFileCatalogsTest() throws Exception {
        given(catalogFileService.getFileCatalogs()).willReturn(catalogList);//service
        this.mockMvc.perform(get(BASE_URL + "/file/catalog")).andDo(print()).andExpect(status().isOk())//controller
                .andExpect(jsonPath("$.size()", is(catalogList.size())));
    }

    @Test
    void processCatalogTest() throws Exception {
        Catalog catalog = new Catalog("PM Traffic Events", "topic5",
                new String[]{"PM_BSC_CER", "PM_BSC_RIR", "PM_BSC_BAR", "PM_BSC_MRR", "PM_MTR_RAW", "PM_MTR"},
                new String[]{"eNB", "gNB"}, Encoding.CSV, "", "");
        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/file/catalog")//controller
                .content(objectMapper.writeValueAsString(catalog)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void returnFileCatalogByIdTest() throws Exception {
        String datatype = "PM Traffic Events";
        String dataNotification = "topic1";
        Catalog catalog = catalogList.get(1);
        given(catalogFileService.getFileCatalogById(datatype, dataNotification)).willReturn(Optional.of(catalog));
        this.mockMvc
                .perform(get(BASE_URL + "/file/catalogbyid").param("datatype", datatype).param("datanotification",
                        dataNotification))//controller
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.dataSubType[0]", is(catalog.getDataSubType()[0])))
                .andExpect(jsonPath("$.encoding".toString(), is(catalog.getEncoding().toString())))
                .andExpect(jsonPath("$.sourceType[0]", is(catalog.getSourceType()[0])))
                .andExpect(jsonPath("$.specRef", is(catalog.getSpecRef())));
    }

}