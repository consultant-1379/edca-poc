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

package com.ericsson.oss.edca.simulators.customcollector.domain;

public enum Encoding {
    CSV("CSV"),
    EXCEL("EXCEL"),
    JSON("JSON"),
    XML("XML"),
    BIN("BIN"),
    ASN1("ASN1"),
    TEXT("TEXT");


    private final String value;

    Encoding(String value) {
        this.value = value;
    }

    public static Encoding fromString(String value) {
        for (Encoding encoding : Encoding.values()) {
            if (encoding.value.equalsIgnoreCase(value)) {
                return encoding;
            }
        }
        return null;
    }

    public String getString() {
        return this.value;
    }
}