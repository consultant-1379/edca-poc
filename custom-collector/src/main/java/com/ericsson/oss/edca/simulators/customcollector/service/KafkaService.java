/*
 * ****************************************************************************** COPYRIGHT Ericsson 2020
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
 * program(s) have been supplied. ****************************************************************************
 */

package com.ericsson.oss.edca.simulators.customcollector.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.ericsson.oss.edca.simulators.customcollector.domain.Notification;
import com.ericsson.oss.edca.simulators.customcollector.domain.NotificationData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaService {
	private static final Logger log = LoggerFactory.getLogger(KafkaService.class);

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private ObjectMapper mapper;

	/**
	 * Send Notification to EDCA Kafka Service.
	 *
	 * @param notification notification to send.
	 */
	public void sendNotification(Notification notification, NotificationData notificationData) {
		String topicName = notificationData.getTopicName();
		log.info("Sending Notifications to Topic: {}", topicName);
		ProducerRecord<String, String> record = null;
		try {
			record = new ProducerRecord<>(topicName, mapper.writeValueAsString(notification));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		kafkaProducer.send(record);
		kafkaProducer.flush();
		log.info("Notification Sent "+notification.toString());
	}
}
