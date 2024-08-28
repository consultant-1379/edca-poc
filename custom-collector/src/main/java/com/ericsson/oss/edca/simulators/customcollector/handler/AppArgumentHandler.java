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

package com.ericsson.oss.edca.simulators.customcollector.handler;

import com.ericsson.oss.edca.simulators.customcollector.common.CLIConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;

import java.util.Set;

public class AppArgumentHandler {
    private static final Logger log = LoggerFactory.getLogger(AppArgumentHandler.class);

    /**
     * Dispatch the appropriate handler based on the provided application argument.
     *
     * @param args
     * @return
     */
    public static String handle(ApplicationArguments args) {
        final Set<String> optionNames = args.getOptionNames();
        if (optionNames.contains(CLIConstants.AUTOMATION)) {
            log.info("Application Running in AUTOMATION mode");
            return CLIConstants.AUTOMATION;
        } else if (optionNames.contains(CLIConstants.INTERACTIVE)) {
            log.info("Application running in INTERACTIVE mode");
            return CLIConstants.INTERACTIVE;
        } else {
            throw new IllegalArgumentException("CCS Execution mode is invalid/missing. Use either --automation or --interactive mode");
        }
    }
}
