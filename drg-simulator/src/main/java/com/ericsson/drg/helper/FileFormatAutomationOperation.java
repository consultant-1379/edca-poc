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

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ericsson.drg.service.Service;

public class FileFormatAutomationOperation {

    private static final Logger logger = LoggerFactory.getLogger(FileFormatAutomationOperation.class);

    /**
     * Convert String into JSON
     * 
     * @param result
     * @return
     */
    private static JSONObject getJSONObject(String result) {
        JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(result);
        } catch (ParseException e) {
            logger.error("Error {} ", e);
        }
        return object;
    }
    
    /**
     * Get Kafka messages using multiple file-format data
     * 
     * @param array
     */
    public static void getKafkaNotification(JSONArray array) {
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            logger.info("File Format : \n {} ", Service.prettyJSONPrint(jsonObject.toJSONString()));                       
            
            JSONObject notificationDetails = (JSONObject) jsonObject.get("notificationTopic");
            String topicName = notificationDetails.get("name").toString();
//            String messageBusId = notificationDetails.get("messageBusId").toString();
            JSONObject messageBus = (JSONObject) notificationDetails.get("messageBus");
//            String url = MessageBusOperation.menuSelection(1) +"/" + messageBusId;
//            ResponseEntity<String> entity = Service.getResult(url);
//            entity = Service.getResult(url);
//            
//            if(entity != null && entity.getStatusCode() == HttpStatus.OK) {
//                JSONObject messageBusDetails = getJSONObject(entity.getBody());
                ArrayList<String> list = (ArrayList) messageBus.get("accessEndpoints");
                
                KafkaOperationsHelper kafkaOperationsHelper = new KafkaOperationsHelper(topicName, list);
                kafkaOperationsHelper.start();
 //           }
            
        }               
    }
    
    /**
     * get File Format and call kafkaNotification function 
     */
    public static void getFileFormate() {

        String url = FileFormatOperations.menuSelection(1);

        ResponseEntity<String> responseEntity = Service.getResult(url);

        if (responseEntity != null) {

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                JSONParser jsonParser = new JSONParser();
                try {
                    Object obj = jsonParser.parse(responseEntity.getBody());
                    JSONArray array = (JSONArray) obj;
                    
                    if(array.isEmpty()) {
                        logger.info("No File Format is available");
                    }
                    
                    getKafkaNotification(array);

                } catch (ParseException e) {
                    logger.error("Error : {} " , e.toString());
                }

            }

        }

    }

}
