/*******************************************************************************
 * COPYRIGHT Ericsson 2020
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
 * program(s) have been supplied.
 ******************************************************************************/

package com.ericsson.edca.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class CatalogServiceApplication {


    public static void main(final String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }


    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {
        };
    }

}
