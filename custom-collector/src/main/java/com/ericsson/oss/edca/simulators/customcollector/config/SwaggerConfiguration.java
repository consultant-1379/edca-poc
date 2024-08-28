/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.edca.simulators.customcollector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ericsson.oss.edca.simulators.customcollector.domain.Notification;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final TypeResolver typeResolver;

    public SwaggerConfiguration(final TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket docketApi() {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select().apis(RequestHandlerSelectors.basePackage("com.ericsson.oss.edca.simulators.customcollector")).build()
                .apiInfo(apiInfo()).additionalModels(typeResolver.resolve(Notification.class));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Custom Collector").description("used to simulate EDCA (non-ENM data) work flow").version("V1.0.0").build();
    }
}