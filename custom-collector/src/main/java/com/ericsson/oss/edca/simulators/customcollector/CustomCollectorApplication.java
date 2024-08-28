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

package com.ericsson.oss.edca.simulators.customcollector;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ericsson.oss.edca.simulators.customcollector.common.CLIConstants;
import com.ericsson.oss.edca.simulators.customcollector.handler.AppArgumentHandler;
import com.ericsson.oss.edca.simulators.customcollector.handler.AutomationHandler;
import com.ericsson.oss.edca.simulators.customcollector.handler.MenuHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class CustomCollectorApplication implements ApplicationRunner {
    @Autowired
    private MenuHandler menuHandler;
    @Autowired
    private AutomationHandler autoHandler;

    @Value("${edca.test.endpointInterface}")
    private String endpointInterface;

    public static void main(String[] args) {
        SpringApplication.run(CustomCollectorApplication.class, args);
        System.exit(1);
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public Scanner input() {
        return new Scanner(System.in);
    }

    @Override
    public void run(ApplicationArguments app) throws Exception {
        String status = AppArgumentHandler.handle(app);
        if (status.equals(CLIConstants.AUTOMATION)) {
            autoHandler.startExecution(endpointInterface);
        } else if (status.equals(CLIConstants.INTERACTIVE)) {
            menuHandler.startExecution();
        }
    }

}
