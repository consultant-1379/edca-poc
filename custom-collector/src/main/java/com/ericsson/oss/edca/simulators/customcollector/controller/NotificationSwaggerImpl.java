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
package com.ericsson.oss.edca.simulators.customcollector.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;;

/**
 * Deprecated Notification Controller used for Swagger OpenAPI Spec generation
 * 
 * @author zvivhar
 *
 */

@RestController
@Api(value = " ", tags = " ", hidden = true, description = " ")
public class NotificationSwaggerImpl {

    @Deprecated
    @ApiOperation(tags = " ", value = " ", notes = " ", nickname = " ", hidden = true)
    @GetMapping("/")
    public void generateSwagger() {
        System.out.println("added for swagger support");
    }

}
