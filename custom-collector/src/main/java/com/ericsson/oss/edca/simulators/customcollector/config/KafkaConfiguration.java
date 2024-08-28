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

package com.ericsson.oss.edca.simulators.customcollector.config;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

@ConfigurationProperties(prefix = "edca.kafka")
public class KafkaConfiguration {
	private String topic;
	private String bootstrapUrl;

	public KafkaConfiguration() {
	}

	@Bean
	public KafkaProducer<String, String> kafkaProducer() {
		Properties properties = new Properties();
		properties.setProperty("sasl.jaas.config",
				"org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin\" password=\"admin_secret\";");
		properties.setProperty("sasl.mechanism", "PLAIN");
		properties.setProperty("security.protocol", "SASL_PLAINTEXT");
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapUrl);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return new KafkaProducer<>(properties);
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getBootstrapUrl() {
		return this.bootstrapUrl;
	}

	public void setBootstrapUrl(String bootstrapUrl) {
		this.bootstrapUrl = bootstrapUrl;
	}

	public String toString() {
		return "KafkaConfiguration( topic=" + this.getTopic() + ", bootstrapUrl=" + this.getBootstrapUrl() + ")";
	}
}
