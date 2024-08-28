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

package com.ericsson.oss.edca.simulators.customcollector.common;

import org.apache.commons.text.TextStringBuilder;

public class MenuConstants {
    private static final String HEADER_01 = "-- Custom Collector Operations --";
    private static final String OPTION_01 = "1. Register BDR information";
    private static final String OPTION_02 = "2. Register Message Bus information";
    private static final String OPTION_03 = "3. Register Notification Topic";

    private static final String OPTION_04 = "4. Register Data Space";
    private static final String OPTION_05 = "5. Register Data Collector";
    private static final String OPTION_06 = "6. Register Data Provider Type";
    private static final String OPTION_07 = "7. Register File Format ";

    private static final String OPTION_08 = "8. Upload files to BDR";
    private static final String OPTION_09 = "9. Send BDR notification to Kafka";
    private static final String OPTION_10 = "10. Upload file to BDR, Send notification to Kafka";

    private static final String OPTION_EXIT = "0. EXIT";
    private static final int PADDING = 5;
    private static final char PADDING_CHAR = ' ';

    public static final TextStringBuilder MENU = new TextStringBuilder().appendln(HEADER_01).appendPadding(PADDING, PADDING_CHAR).appendln(OPTION_01).appendPadding(PADDING, PADDING_CHAR)
            .appendln(OPTION_02).appendPadding(PADDING, PADDING_CHAR).appendln(OPTION_03).appendPadding(PADDING, PADDING_CHAR)

            .appendln(OPTION_04).appendPadding(PADDING, PADDING_CHAR).appendln(OPTION_05).appendPadding(PADDING, PADDING_CHAR).appendln(OPTION_06).appendPadding(PADDING, PADDING_CHAR)

            .appendln(OPTION_07).appendPadding(PADDING, PADDING_CHAR)

            .appendln(OPTION_08).appendPadding(PADDING, PADDING_CHAR).appendln(OPTION_09).appendPadding(PADDING, PADDING_CHAR).appendln(OPTION_10).appendPadding(PADDING, PADDING_CHAR)
            //.appendln(OPTION_11).appendPadding(PADDING, PADDING_CHAR).appendln(OPTION_12).appendPadding(PADDING, PADDING_CHAR).appendln(OPTION_13).appendPadding(PADDING, PADDING_CHAR)

            .appendln(OPTION_EXIT);

    private MenuConstants() {
    }
}
