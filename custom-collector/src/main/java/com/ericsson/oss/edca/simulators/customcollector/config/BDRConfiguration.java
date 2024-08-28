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

package com.ericsson.oss.edca.simulators.customcollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
@ConfigurationProperties(prefix = "edca.bdr")
public class BDRConfiguration {
    private String host;
    private Integer port;
    private String accesskey;
    private String secretkey;
    private String bucket;

    public BDRConfiguration() {
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(this.host, this.port, false).credentials(this.accesskey, this.secretkey).build();

    }

    public String getBDRAccessEndpoint() {
        return host + ":" + port;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAccesskey() {
        return this.accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getSecretkey() {
        return this.secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BDRConfiguration;
    }

    public String toString() {
        return "BDRConfiguration(host=" + this.getHost() + ", port=" + this.getPort() + ", accesskey=" + this.getAccesskey() + ", secretkey=" + this.getSecretkey() + ", bucket=" + this.getBucket()
                + ")";
    }
}
