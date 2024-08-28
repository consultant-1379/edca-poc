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

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "edca.catalog")
public class CatalogConfiguration {

    private String host;

    // private Integer port;

    public CatalogConfiguration() {
    }

    @Bean
    public WebTarget webTarget() {
        return ClientBuilder.newClient(new ClientConfig()).target("http://" + this.host);

    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /*
     * public Integer getPort() { return this.port; }
     * 
     * public void setPort(Integer port) { this.port = port; }
     */

    /*
     * public String toString() { return "CatalogConfiguration(host=" + this.getHost() + ", port=" + this.getPort() + ")"; }
     */
}
