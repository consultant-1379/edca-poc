
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

package com.ericsson.oss.edca.simulators.customcollector.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.oss.edca.simulators.customcollector.config.BDRConfiguration;

import io.minio.*;
import io.minio.errors.*;

@Component
public class BDRService {
    private static final Logger log = LoggerFactory.getLogger(BDRService.class);

    @Autowired
    private BDRConfiguration bdr;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private CatalogService catalogService;

    /**
     * Upload data-file to BDR.
     *
     * @param objectName
     *            name of the object
     * @param filePath
     *            file to be uplaoded
     * @return weather the file was sucessfuly uploaded to BDR.
     * @throws Exception
     */
    public boolean uploadFileToBDR(String objectName, String filePath) throws Exception {
        try {
            log.info("Uploading file to BDR: {}", objectName);
            if (bucketExists()) {

                uploadObject(objectName, filePath);
                log.info("Uploading file to BDR: success");
                return true;
            } else {
                log.error("Bucket: {} DOES NOT EXIST", bdr.getBucket());
                throw new NullPointerException("Bucket: " + bdr.getBucket() + " DOES NOT EXIST");
            }
        } catch (Exception e) {
            log.error("Upload file = FAILED {}", e.getMessage());
            throw e;
        }
    }

    private boolean bucketExists() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException,
            XmlParserException, InvalidBucketNameException, ErrorResponseException {

        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bdr.getBucket()).build());
    }

    private void uploadObject(String objectName, String filePath) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException,
            ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {

        minioClient.uploadObject(UploadObjectArgs.builder().bucket(bdr.getBucket()).object(objectName).filename(filePath).build());
    }

}
