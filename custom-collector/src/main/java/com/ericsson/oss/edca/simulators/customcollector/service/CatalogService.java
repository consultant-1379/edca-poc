/*
 * ******************************************************************************
 *  COPYRIGHT Ericsson 2020
 *
 *
 *
 *  The copyright to the computer program(s) herein is the property of
 *
 *  Ericsson Inc. The programs may be used and/or copied only with written
 *
 *  permission from Ericsson Inc. or in accordance with the terms and
 *
 *  conditions stipulated in the agreement/contract under which the
 *
 *  program(s) have been supplied.
 * ****************************************************************************
 */

package com.ericsson.oss.edca.simulators.customcollector.service;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.oss.edca.simulators.customcollector.common.CatalogConstants;
import com.ericsson.oss.edca.simulators.customcollector.domain.*;

@Component
public class CatalogService {
    private static final Logger log = LoggerFactory.getLogger(CatalogService.class);
    @Autowired
    private WebTarget webTarget;

    public boolean uploadNotificationData(NotificationData notificationData) {
        log.info("Uploading NotificationData to Catalog");
        try {
            Response response = webTarget.request().post(Entity.entity(notificationData, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 201) {
                log.info("Uploading NotificationData: success");
                return true;
            } else if (response.getStatus() == 406) {
                log.info("Uploading NotificationData: topicName={} and dataCategory={} already exists", notificationData.getTopicName(), notificationData.getDataCategory());
                return true;
            } else {
                log.warn("Uploading NotificationData: failed with http response={}", response.getStatus());
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to upload NotificationData with reason={}", e.getMessage());
            return false;
        }

    }

    public int registerBDRInformation(BulkDataRepository bulkDataRepository) {
        try {

            Response response = webTarget.path(CatalogConstants.BDRINFO_URI).request().post(Entity.entity(bulkDataRepository, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 201) {
                BulkDataRepositoryDTO bulkDataRepositoryDTO = response.readEntity(BulkDataRepositoryDTO.class);
                log.info("Registered Successfully. Response is {}, {}", response.getStatus(), bulkDataRepositoryDTO.toString());
                return bulkDataRepositoryDTO.getId();
            } else {
                log.warn("Register BDR Information: failed with http response={}, {}", response.getStatus(), response.readEntity(String.class));
                return -1;
            }
        } catch (Exception e) {
            log.error("Failed to Register BDR Information with reason={}", e.getMessage());
            return -1;
        }

    }

    public int registerMessageBus(MessageBus messageBus) {

        try {

            Response response = webTarget.path(CatalogConstants.MESSAGEBUSINFO_URI).request().post(Entity.entity(messageBus, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 201) {
                MessageBusDto messageBusDto = response.readEntity(MessageBusDto.class);
                log.info("Registered Successfully. Response is {}, {}", response.getStatus(), messageBusDto.toString());
                return messageBusDto.getId();
            } else {
                log.warn("Register messageBus Information: failed with http response={}, {}", response.getStatus(), response.readEntity(String.class));
                return -1;
            }
        } catch (Exception e) {
            log.error("Failed to Register messageBus Information with reason={}", e.getMessage());

            return -1;
        }

    }

    public int registerNotificationTopic(NotificationTopic notificationTopic) {

        try {
            Response response = webTarget.path(CatalogConstants.NOTIFICATIONINFO_URI).request().post(Entity.entity(notificationTopic, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 201) {
                NotificationTopicDto notificationTopicDto = response.readEntity(NotificationTopicDto.class);

                log.info("Registered Successfully. Response is {}, {}", response.getStatus(), notificationTopicDto.toString());
                return notificationTopicDto.getId();

            } else {
                log.warn("Register notificationTopic Information: failed with http response={}, {}", response.getStatus(), response.readEntity(String.class));
                return -1;
            }
        } catch (Exception e) {
            log.error("Failed to Register notificationTopic Information with reason={}", e.getMessage());
            return -1;
        }

    }

    public boolean registerDataCollector(DataCollector dataCollector) {

        try {
            Response response = webTarget.path(CatalogConstants.DATACOLLECTOR_URI).request().post(Entity.entity(dataCollector, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 201) {
                log.info("Registered Successfully. Response is: {}, DataCollectorDto [{}]", response.getStatus(), response.readEntity(String.class));
                return true;
            } else {
                log.warn("Register Data Collector Information: failed with http response={}, {}", response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to Register Data Collector Information with reason={}", e.getMessage());
            return false;
        }

    }

    public int registerDataProviderType(DataProviderType dataProviderType) {

        try {
            Response response = webTarget.path(CatalogConstants.DATAPROVIDERTYPE_URI).request().post(Entity.entity(dataProviderType, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 201) {
                DataProviderTypeDto dataProviderTypeDto = response.readEntity(DataProviderTypeDto.class);
                log.info("Registered Successfully. Response is {}, {}", response.getStatus(), dataProviderTypeDto.toString());
                return dataProviderTypeDto.getId();
            } else {
                log.warn("Register Data Provider Type Information: failed with http response={}, {}", response.getStatus(), response.readEntity(String.class));
                return -1;
            }
        } catch (Exception e) {
            log.error("Failed to Register Data Provider Type Information with reason={}", e.getMessage());
            return -1;
        }

    }

    public boolean registerFileFormat(FileFormat fileFormat) {

        try {

            Response response = webTarget.path(CatalogConstants.FILEFORMAT_URI).request().post(Entity.entity(fileFormat, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 201) {
                log.info("Registered Successfully. Response is: {}, FileFormatDto [{}]", response.getStatus(), response.readEntity(String.class));
                return true;
            } else {
                log.warn("Register File Format Information: failed with http response={}, {}", response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to Register File Format Information with reason={}", e.getMessage());
            return false;
        }

    }

    public int registerDataSpace(DataSpace dataSpace) {

        try {

            Response response = webTarget.path(CatalogConstants.DATASPACE_URI).request().post(Entity.entity(dataSpace, MediaType.APPLICATION_JSON));
            if (response.getStatus() == 201) {
                DataSpaceDto dataSpaceDto = response.readEntity(DataSpaceDto.class);
                log.info("Registered Successfully. Response is {}, {}", response.getStatus(), dataSpaceDto.toString());
                return dataSpaceDto.getId();
            } else {
                log.warn("Register dataSpace Information: failed with http response={}, {}", response.getStatus(), response.readEntity(String.class));
                return -1;
            }
        } catch (Exception e) {
            log.error("Failed to Register dataSpace Information with reason={}", e.getMessage());
            return -1;
        }
    }

    public void getMetadata() {
        webTarget.request().get();
    }

}
