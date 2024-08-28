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

package com.ericsson.drg.utility;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ericsson.drg.constants.Constants;
import com.ericsson.drg.model.Metadata;

public class DRGUtility {

    private static final Logger logger = LoggerFactory.getLogger(DRGUtility.class.getName());
    private static List<Metadata> metadataList = new ArrayList<Metadata>(); 
    private static final Map<String, Object> commandLineParams = new HashMap<>();
    
    /**
     * To initialize arguments
     * @param args
     */
    public static void initialize(final String args[]) {
        parseRuntimeArgs(args);
    }

    /**
     * Parse Runtime Arguments
     * @param args
     */
    private static void parseRuntimeArgs(final String args[]) {
        final List<String> argsList = Arrays.asList(args);
        try {
            argsList.stream().forEach((final String arg) -> {
                final String strippedDoubleDashDelim = arg.substring(2);
                final String[] splitByEqualCmdKeyValuePair = strippedDoubleDashDelim.split("=");
                //validate command line arg key.value pairs
                if (!Arrays.asList(Constants.VALID_COMMAND_ARGS).contains(splitByEqualCmdKeyValuePair[0])) {
                    logger.error("Invalid command line arg : {}", splitByEqualCmdKeyValuePair[0]);
                    System.exit(1);
                }
                if(splitByEqualCmdKeyValuePair.length == 2)
                    commandLineParams.put(splitByEqualCmdKeyValuePair[0], splitByEqualCmdKeyValuePair[1]);
                else
                    commandLineParams.put(splitByEqualCmdKeyValuePair[0], null);        
            });
            logger.debug("Command Line execution parameters : {}", commandLineParams);
        } catch (final Exception e) {
            logger.error("Error in parsing command line arguments : {}", e.getMessage());
            System.exit(1);
        }
        parseAndValidateExecConfigParams();

    }

    /**
     * Parse and Validate Passed Arguments
     */
    private static void parseAndValidateExecConfigParams() {
        final Map<String, String> availableSystemEnv = System.getenv();
        boolean isMissingRequiredConfig = false;
        final List<String> missingProperties = new ArrayList<String>();
        for (final String requiredExecConfigParam : Constants.REQUIRED_EXECUTION_CONFIG_PARAMS) {
            if (!availableSystemEnv.containsKey(requiredExecConfigParam)
                    || availableSystemEnv.get(requiredExecConfigParam) == null
                    || availableSystemEnv.get(requiredExecConfigParam).toString().isEmpty()) {
                isMissingRequiredConfig = true;
                missingProperties.add(requiredExecConfigParam);
            } else {
                commandLineParams.put(requiredExecConfigParam, availableSystemEnv.get(requiredExecConfigParam));
            }
        }
        if (isMissingRequiredConfig) {
            logger.error("Set the missing properties {} in values.yaml OR specify via helm install --set key=value OR directly initialize inside pod",
                    missingProperties);
            System.exit(1);
        }
    }

    /**
     * Get List of Metadata
     */
    public static List<Metadata> getMetadata() {

        metadataList.clear();
        final RestTemplate restTemplate = new RestTemplate();
        List<Metadata> currentMetadataList = null;
        try {
            final ResponseEntity<Metadata[]> catalogGetRespEntitities = restTemplate
                    .getForEntity(new URI(commandLineParams.get(Constants.CATALOG_URL).toString()), Metadata[].class);
            currentMetadataList = Arrays.asList(catalogGetRespEntitities.getBody());
        } catch (final RestClientException restClientException) {
            logger.error("Error in fetching catalog metadata items . Exception : {}", restClientException.getMessage());
        } catch (final URISyntaxException e) {
            logger.error("Incorrect URI configured for catalog service . Exception : {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error : {}" , e.getMessage());
        }
        if (currentMetadataList != null && !currentMetadataList.isEmpty()) {
            metadataList.addAll(currentMetadataList);
        }
        return metadataList;
    }

    /**
     * Fetch Topic and Address from Metadata
     * @return topic and Address Map
     */
    public static Map<String, List<String>> getTopicAndAddress() {
        final Map<String, List<String>> topicAddressMap = new HashMap<String, List<String>>();
        if (metadataList.isEmpty()) {
            logger.info("No MetaData Available");
        } else {
            for (final Metadata metadata : metadataList) {
                topicAddressMap.put(metadata.getTopicName(), new ArrayList<String>(metadata.getAccessEndpoints()));
            }
        }
        return topicAddressMap;
    }



    /**
     * @return the commandlineexecutionparams
     */
    public static Map<String, Object> getCommandLineParams() {
        return commandLineParams;
    }
}
