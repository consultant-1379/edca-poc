/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2021
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.drg.helper;

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.ericsson.drg.constants.Constants;
import com.ericsson.drg.service.Service;

public class DmaapKafkaMessages {

    private static final Logger logger = LoggerFactory.getLogger(DmaapKafkaMessages.class);
       
    
    /**
     * Iterate in all messages
     * @param jsonArray
     */
    private void getMessages(JSONArray jsonArray) {
        for (Object object : jsonArray.toArray()) {
            try {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse((String) object);

                final JSONArray objectArray = (JSONArray) jsonObject
                        .get(Constants.NOTIFICATION_OBJECTNAME);
                final String endpointInterface = jsonObject
                        .get(Constants.NOTIFICATION_ENDPOINTINTERFACE).toString().trim();

                if (endpointInterface.equalsIgnoreCase("SFTP")) {

                    final String accessEndpoint = jsonObject.get(Constants.NOTIFICATION_ACCESSENDPOINT)
                            .toString().trim();
                    final String filePath = jsonObject.get(Constants.NOTIFICATION_FILEPATH)
                            .toString().trim();
                    Service.downloadFileFromSFTP(accessEndpoint+filePath, objectArray);

                } else if (endpointInterface.equalsIgnoreCase("MINIO")) {
                    final String filePath = jsonObject.get(Constants.NOTIFICATION_FILEPATH)
                            .toString().trim();
                    final String accessEndpoint = jsonObject.get(Constants.NOTIFICATION_ACCESSENDPOINT)
                            .toString().trim();
                    Service.downloadFileFromMinio(filePath, objectArray, accessEndpoint);
                } else {
                    logger.error("EndPoint is not available");
                }
            } catch (ParseException e) {
               logger.error("Error : {} ", e.toString());
            }

        }
    }
    
    
    /**
     * Get Messages from Dmaap
     * @param topicName
     * @param urls
     */
    public void getNotifications(String topicName, Set<String> urls) {
        for (String url : urls) {
            String result;
            url  += topicName + "/CGI/C1?timeout=1000";
            ResponseEntity<String> responseEntity;
            boolean flag = true;
            while (flag) {
                try {
                    logger.info("Url : {} ", url);
                    responseEntity = Service.getResult(url) ;
                          
                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        result = responseEntity.getBody();
                        logger.info("Consumed message : \n {}", Service.prettyJSONPrint(result));

                        try {
                            JSONArray jsonArray = (JSONArray) new JSONParser().parse(result);
                            logger.info("Consumed messages : {} ", jsonArray.size());
                            if (jsonArray.isEmpty()) {
                                break;
                            }
                            this.getMessages(jsonArray);
                        } catch (ParseException e) {
                            logger.error("Error : {} ", e.toString());
                        }
                    } else {
                        flag = false;
                    }

                } catch (ResourceAccessException e) {
                    logger.error("Connection not established - Error : {}", e.getMessage());
                    flag = false;
                } catch (HttpClientErrorException e) {
                    logger.error("No such topic exists. : {}", e.getMessage());
                    flag = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = false;
                } 
            }
        }

    }

}
