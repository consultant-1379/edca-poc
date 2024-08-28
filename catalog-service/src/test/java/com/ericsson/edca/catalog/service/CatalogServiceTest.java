package com.ericsson.edca.catalog.service;

import com.ericsson.edca.catalog.controller.CatalogController;
import com.ericsson.edca.catalog.model.Catalog;
import com.ericsson.edca.catalog.model.Encoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@WebMvcTest(controllers = CatalogController.class)
class CatalogServiceTest {

    private static final String BASE_URL = "/api/v1/edca";
    @Autowired
    private MockMvc mockMvc;
    @Value("${info.app.description}")
    private String description;
    @MockBean
    private CatalogService catalogService;
    @Autowired
    private ObjectMapper objectMapper;
    private List<Catalog> catalogList;

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @BeforeEach
    void setUp() {
        this.catalogList = new ArrayList<Catalog>();
        this.catalogList.add(new Catalog("PM Traffic Events", "topic1",
                new String[]{"PM_BSC_CER", "PM_BSC_RIR", "PM_BSC_BAR", "PM_BSC_MRR", "PM_MTR_RAW", "PM_MTR"},
                new String[]{"eNB", "gNB"},
                Encoding.JSON, "", ""));

        this.catalogList.add(new Catalog("Log", "topic1",
                new String[]{"PM_BSC_PERFORMANCE_EVENT_STATISTICS", "PM_BSC_PERFORMANCE_CTRL", "PM_RTT"},
                new String[]{"gNB"},
                Encoding.CSV, "", ""));

        this.catalogList.add(new Catalog("PM Traffic Events", "topic2",
                new String[]{"PM_GPEH", "PM_STATISTICAL_RAW", "PM_BSC_CTR_RAW", "PM_BSC_CER_RAW"},
                new String[]{"eNB"},
                Encoding.EXCEL, "", ""));
    }

    //get all catalogs
    @Test
    void getAllCatalogsTest() throws Exception {
        given(catalogService.getCatalogs()).willReturn(catalogList);
        this.mockMvc.perform(get(BASE_URL + "/catalog"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(catalogList.size())));
    }

    //get specific catalog test
    @Test
    void getCatalogByIdTest() throws Exception {
        String datatype = "PM Traffic Events";
        String dataNotification = "topic1";
        Catalog catalog = catalogList.get(1);
        given(catalogService.getCatalogById(datatype, dataNotification)).willReturn(Optional.of(catalog));
        this.mockMvc.perform(get(BASE_URL + "/catalogbyid")
                .param("datatype", datatype)
                .param("datanotification", dataNotification))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataSubType[0]", is(catalog.getDataSubType()[0])))
                .andExpect(jsonPath("$.encodingType".toString(), is(catalog.getEncoding().toString())))
                .andExpect(jsonPath("$.sourceType[0]", is(catalog.getSourceType()[0])))
                .andExpect(jsonPath("$.specRefUrl", is(catalog.getSpecRef())));
    }

    @Test
    void getCatalogByIdNegativeTest() throws Exception {
        String datatype = "PM Traffic Events";
        String dataNotification = "topic4";
        given(catalogService.getCatalogById(datatype, dataNotification)).willReturn(Optional.empty());
        //this.mockMvc.perform(get(BASE_URL+"/catalog/{datatype}/{datanotification}",datatype,dataNotification))
        this.mockMvc.perform(get(BASE_URL + "/catalogbyid")
                .param("datatype", datatype)
                .param("datanotification", dataNotification))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createCatalogTest() throws Exception {
        Catalog catalog = new Catalog("PM Traffic Events", "topic5",
                new String[]{"PM_BSC_CER", "PM_BSC_RIR", "PM_BSC_BAR", "PM_BSC_MRR", "PM_MTR_RAW", "PM_MTR"},
                new String[]{"eNB", "gNB"},
                Encoding.JSON, "", "");
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/catalog")
                .content(objectMapper.writeValueAsString(catalog))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createCatalogNullEnumTest() throws Exception {
        Catalog catalog = new Catalog("PM Traffic Events", "topic6",
                new String[]{"PM_BSC_CER", "PM_BSC_RIR", "PM_BSC_BAR", "PM_BSC_MRR"},
                new String[]{"eNB", "gNB"},
                null, "", "");
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/catalog")
                .content(objectMapper.writeValueAsString(catalog))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createCatalogBadRequestTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/catalog")
                .content(objectMapper.writeValueAsString(null))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCatalogTest() throws Exception {
        Catalog catalog = new Catalog("PM Traffic Events", "topic1",
                new String[]{"PM_BSC_CER", "PM_BSC_RIR", "PM_BSC_BAR", "PM_BSC_MRR", "PM_MTR_RAW", "PM_MTR"},
                new String[]{"eNB", "gNB"},
                Encoding.JSON, "", "");
        this.mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/catalog")
                .content(objectMapper.writeValueAsString(catalog))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateCatalogBadRequestTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + "/catalog")
                .content(objectMapper.writeValueAsString(null))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //put create
    @Test
    void createCatalogPutTest() throws Exception {
        Catalog catalog = new Catalog("PM Traffic Events", "topic8",
                new String[]{"PM_BSC_CER", "PM_BSC_RIR", "PM_BSC_BAR", "PM_BSC_MRR", "PM_MTR_RAW", "PM_MTR"},
                new String[]{"eNB", "gNB"},
                Encoding.JSON, "", "");
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + "/catalog")
                .content(objectMapper.writeValueAsString(catalog))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    //put update
    @Test
    void updateCatalogPutTest() throws Exception {
        Catalog catalog = new Catalog("PM Traffic Events", "topic1",
                new String[]{"PM_BSC_CER", "PM_BSC_RIR", "PM_BSC_BAR", "PM_BSC_MRR", "PM_MTR_RAW", "PM_MTR"},
                new String[]{"eNB", "gNB"},
                Encoding.EXCEL, "", "");
        this.mockMvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + "/catalog")
                .content(objectMapper.writeValueAsString(catalog))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
