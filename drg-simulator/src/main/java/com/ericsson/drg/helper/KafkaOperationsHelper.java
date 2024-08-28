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

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.drg.constants.Constants;
import com.ericsson.drg.service.Service;

/**
 * Thread to Consume Kafka Messages
 *
 * @param index No ,Kafka topic Name, Kafka Address
 */
public class KafkaOperationsHelper extends Thread {
    private String topic;
    private List<String> address;
    private KafkaConsumer<String, String> consumer;
    private boolean flag;
    private static final Logger logger = LoggerFactory.getLogger(KafkaOperationsHelper.class.getName());

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public KafkaOperationsHelper(final String topic, final List<String> address) {
        this.topic = topic;
        this.address = address;
        this.flag = true;
    }

    

    /**
     * Run Kafka Consumer
     */
    @Override
    public void run() {
        try {
            consumer = new KafkaConsumer<>(Service.setProperties(address,topic));
            consumer.subscribe(Arrays.asList(topic));

            while (flag) {
                final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
                if (records == null || records.isEmpty()) {
                    logger.info("No file notifications received in current poll - Retrying");
                }
                for (final ConsumerRecord<String, String> record : records) {
                    logger.info("File notification received for DRG thread {} : \n {}", Thread.currentThread().getName(),
                            Service.prettyJSONPrint(record.value()));
                    try {
                        JSONObject jsonObject;
                        final Object obj = new JSONParser().parse(record.value());
                        jsonObject = (JSONObject) obj;

                        final JSONArray objectArray = (JSONArray) jsonObject.get(Constants.NOTIFICATION_OBJECTNAME);
                        final String endpointInterface = jsonObject.get(Constants.NOTIFICATION_ENDPOINTINTERFACE)
                                .toString().trim();

                        if (endpointInterface.equalsIgnoreCase("SFTP")) {

                            final String accessEndpoint = jsonObject.get(Constants.NOTIFICATION_ACCESSENDPOINT)
                                    .toString().trim();
                            final String filePath = jsonObject.get(Constants.NOTIFICATION_FILEPATH).toString()
                                    .trim();
                            Service.downloadFileFromSFTP(accessEndpoint+filePath, objectArray);

                        } else if (endpointInterface.equalsIgnoreCase("MINIO")) {
                            final String filePath = jsonObject.get(Constants.NOTIFICATION_FILEPATH).toString()
                                    .trim();
                            final String accessEndpoint = jsonObject.get(Constants.NOTIFICATION_ACCESSENDPOINT)
                                    .toString().trim();
                            Service.downloadFileFromMinio(filePath, objectArray, accessEndpoint);
                        } else {
                            logger.error("Endpoint is not available");
                        }
                    } catch (Exception e) {
                        logger.error("Notification is not in proper format : {} ", e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.toString());

        } finally {
            setFlag(false);
            if (consumer != null) {
                consumer.close();
            }
            logger.info(" {} is Un-Subscribed ", topic);
        }

    }

}
