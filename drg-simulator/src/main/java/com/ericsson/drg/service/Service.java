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

package com.ericsson.drg.service;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.ericsson.drg.helper.MinioOperationsHelper;
import com.ericsson.drg.helper.SFTPOperationsHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Service {

    private static final Logger logger = LoggerFactory.getLogger(Service.class);
    
    /**
     * Get url in String and return ResponceEntity
     * @param url
     * @return responseEntity
     */
    public static ResponseEntity<String> getResult(String url) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(url, String.class);
        } catch (Exception e) {
            logger.error("Error : {} ", e.toString());
        }
        return responseEntity;
    }

    /**
     * print json object in pretty format
     * @param object
     * @return result
     */
    public static String prettyJSONPrint(Object object) {
        String result = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.toJson(new JSONParser().parse(object.toString()));
        } catch (Exception e) {
            logger.error("Error : {} ", e.toString());
        }
        return result;
    }
    
    
    /**
     * print Get Catalog API's using url
     * @param url
     */
    public static void printGetCatalog(String url) {
        
        if (url != null) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        String result = responseEntity.getBody();
                        logger.info("Result : {} \n{}", responseEntity.getStatusCode() ,prettyJSONPrint( result));
                    } else {
                        logger.error("Error in Request : ");
                    }
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                logger.error("Error : {} ", e.getMessage());
            } catch (Exception e) {
                logger.error("Error : Requested Resource not found : {} ",e.toString());
            }
        }
        
    }
    
    
    /**
     * Make call to download MINIO
     * 
     * @param bucketName
     * @param objectArray
     */
    public static void downloadFileFromMinio(String bucketName, JSONArray objectArray, String accessEndpoint) {

        logger.info("Bucket Name : {}", bucketName);
        if (!objectArray.isEmpty()) {
            for (int i = 0; i < objectArray.size(); i++) {
                logger.info("Object name : {} ", objectArray.get(i));
                final MinioOperationsHelper minioOperationsHelper = new MinioOperationsHelper(bucketName,
                        objectArray.get(i).toString(), accessEndpoint);
                minioOperationsHelper.downloadObject();
            }
        } else {
            logger.error("No object file available in notification");
        }
    }
    
    /**
     * Make call to download from SFTP
     * 
     * @param accessEndpoint
     * @param objectArray
     */
    public static void downloadFileFromSFTP(String accessEndpoint, JSONArray objectArray) {

        if (!accessEndpoint.isEmpty()) {
            if (!objectArray.isEmpty()) {
                for (int i = 0; i < objectArray.size(); i++) {
                    logger.info("Download object name : {} ", objectArray.get(i));
                    final SFTPOperationsHelper sftpOperationsHelper = new SFTPOperationsHelper(accessEndpoint,
                            objectArray.get(i).toString());
                    sftpOperationsHelper.downloadFile();
                }
            } else {
                logger.info("No object to download");
            }
        } else {
            logger.error("AccessEndpoint is not available");
        }
    }
    
    /**
     * Set Kafka Properties
     * 
     * @return Properties
     */
    public static Properties setProperties(List<String> address,String topic) {
        Properties properties = new Properties();
        properties.setProperty("sasl.jaas.config",
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin\" password=\"admin_secret\";");
        properties.setProperty("sasl.mechanism", "PLAIN");
        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", address));
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group-" + topic);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return properties;
    }
    
}
