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
package com.ericsson.drg.constants;

/**
 * Constants Used in this application
 * 
 * @author zkanris
 *
 */
public class Constants {

    public static final String NOTIFICATION_ENDPOINTINTERFACE = "endpointInterface";
    public static final String NOTIFICATION_FILEPATH = "filePath";
    public static final String NOTIFICATION_ACCESSENDPOINT = "accessEndpoint";
    public static final String NOTIFICATION_OBJECTNAME = "objectname";
    public static final String CATALOG_URL = "CATALOG_URL";
    public static final String[] VALID_COMMAND_ARGS = { "interactive", "i", "a" };
    // public static final String[] REQUIRED_EXECUTION_CONFIG_PARAMS = {
    // "CATALOG_URL", "CATALOG_ENDPOINT", "MINIO_URL",
    // "MINIO_PORT", "MINIO_ACCESSKEY", "MINIO_SECRETKEY", "SFTP_USER", "SFTP_PASS"
    // };
    public static final String SFTP_DOWNLOAD_FOLDER = "sftp_downloads";
    public static final String MINIO_DOWNLOAD_FOLDER = "minio_downloads";
    public static final String INTERACTIVE_MODE = "interactive";
    public static final String BDR_ENDPOINT = "BDR_ENDPOINT";
    public static final String DATAPROVIDER_ENDPOINT = "DATAPROVIDER_ENDPOINT";
    public static final String DATASPACE_ENDPOINT = "DATASPACE_ENDPOINT";
    public static final String FILEFORMAT_ENDPOINT = "FILEFORMAT_ENDPOINT";
    public static final String MESSAGEBUS_ENDPOINT = "MESSAGEBUS_ENDPOINT";
    public static final String NOTIFICATIONTOPIC_ENDPOINT = "NOTIFICATIONTOPIC_ENDPOINT";
    public static final String[] REQUIRED_EXECUTION_CONFIG_PARAMS = { CATALOG_URL,  BDR_ENDPOINT ,
            DATAPROVIDER_ENDPOINT, DATASPACE_ENDPOINT , FILEFORMAT_ENDPOINT, MESSAGEBUS_ENDPOINT, NOTIFICATIONTOPIC_ENDPOINT,
             "MINIO_ACCESSKEY", "MINIO_SECRETKEY","SFTP_USER", "SFTP_PASS" };
}
