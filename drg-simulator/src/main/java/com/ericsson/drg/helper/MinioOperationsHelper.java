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

package com.ericsson.drg.helper;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.drg.constants.Constants;
import com.ericsson.drg.utility.DRGUtility;

import io.minio.BucketExistsArgs;
import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;

/**
 * Thread to download Object given bucket Name and Object Name
 */
public class MinioOperationsHelper {

    private String bucketName;
    private String objectName;
    private String url;
    private static final Logger logger = LoggerFactory.getLogger(MinioOperationsHelper.class.getName());

    public MinioOperationsHelper(final String bucketName, final String objectName,final String url) {
        
        this.bucketName = bucketName;
        this.objectName = objectName;
        this.url = url;
    }
    /**
     * Minio Download Function
     */
    public void downloadObject() {

//        final String url = DRGUtility.getCommandLineParams().get("MINIO_URL").toString();
//        final String port = DRGUtility.getCommandLineParams().get("MINIO_PORT").toString();
        final String accesskey = DRGUtility.getCommandLineParams().get("MINIO_ACCESSKEY").toString();
        final String secretkey = DRGUtility.getCommandLineParams().get("MINIO_SECRETKEY").toString();
        try {

            logger.info("Downloading object from BDR started");
            logger.info("BDR store url : {}  ", url);

            if (bucketName != null && objectName != null && url != null && accesskey != null && secretkey != null ) { // && port != null
                File file = new File(Constants.MINIO_DOWNLOAD_FOLDER);
                file.mkdir();
                final String downloadPath = "./" + Constants.MINIO_DOWNLOAD_FOLDER +"/" + objectName;
                //final MinioClient minioClient = MinioClient.builder().endpoint(url, Integer.parseInt(port), false).credentials(accesskey, secretkey)
                 //       .build();
                final MinioClient  minioClient = MinioClient.builder().endpoint(url).credentials(accesskey, secretkey).build();

                boolean isExist = false;
                try {
                    isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

                } catch (final IllegalArgumentException e) {
                    logger.error("Bucket name is invalid");
                } catch (final Exception e) {
                    logger.error("Error in Bucket initialisation : {}", e.getMessage());
                }
                if (isExist) {
                    try {
                        minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).filename(downloadPath).build());
                        logger.info(" {} is downloaded Successfully", objectName);
                    } catch (final Exception e) {
                        logger.error("{} does not exist in BDR", objectName);
                    }

                } else {
                    logger.warn("Bucket doesn't exist");
                }
            }
        } catch (

        final Exception e) {
            logger.error(" Wrong Port no  {}", e.getMessage());
        }

    }

}
