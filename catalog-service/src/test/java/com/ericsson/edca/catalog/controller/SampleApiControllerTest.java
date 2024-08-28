/*******************************************************************************
 * COPYRIGHT Ericsson 2020
 *
 *
 *
 * The copyright to the computer program(s) herein is the property of
 *
 * Ericsson Inc. The programs may be used and/or copied only with written
 *
 * permission from Ericsson Inc. or in accordance with the terms and
 *
 * conditions stipulated in the agreement/contract under which the
 *
 * program(s) have been supplied.
 ******************************************************************************/

/*
 * package com.ericsson.oss.edca.catalog.controller;
 *
 * import static org.assertj.core.api.Assertions.assertThat;
 *
 * import javax.ws.rs.core.MediaType;
 *
 * import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
 * import static
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 *
 * import org.junit.Before; import org.junit.Test; import
 * org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootTest; //import
 * org.springframework.http.MediaType; import
 * org.springframework.test.context.junit4.SpringRunner; import
 * org.springframework.test.web.servlet.MockMvc; import
 * org.springframework.test.web.servlet.setup.MockMvcBuilders; import
 * org.springframework.web.context.WebApplicationContext;
 *
 * import com.ericsson.oss.edca.catalog.CatalogServiceApplication; import
 * com.ericsson.oss.edca.catalog.catalog.service.catalog.controller.example.
 * SampleApiControllerImpl; import
 * com.ericsson.oss.edca.catalog.controller.CatalogController;
 *
 * @RunWith(SpringRunner.class)
 *
 * @SpringBootTest(classes = {CatalogServiceApplication.class,
 * SampleApiControllerImpl.class}) public class SampleApiControllerTest {
 *
 * @Autowired private WebApplicationContext webApplicationContext; private
 * MockMvc mvc;
 *
 * @Autowired private SampleApiControllerImpl sampleApiController;
 *
 * @Before public void setUp() { mvc =
 * MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); }
 *
 * @Test public void sample() throws Exception {
 * mvc.perform(get("/v1/sample").contentType(MediaType.APPLICATION_JSON)).
 * andExpect(status().isOk()) .andExpect(content().string("Sample response")); }
 *
 * @Autowired private CatalogController controller;
 *
 * @Test public void contexLoads() throws Exception {
 * assertThat(controller).isNotNull(); } }
 */