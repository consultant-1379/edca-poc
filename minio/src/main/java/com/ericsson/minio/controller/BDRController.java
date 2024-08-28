/*******************************************************************************
 * COPYRIGHT Ericsson 2021
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

package com.ericsson.minio.controller;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.minio.service.BDRService;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Bucket;
import io.minio.messages.Item;

public class BDRController {

    private String accesskey;

    private String secretkey;


    Map<String,String> env = new HashMap<>();

    private BDRService service;
    public static final String MINIO_DOWNLOAD_FOLDER = "minio_downloads";

    private static Logger logger = LoggerFactory.getLogger(BDRController.class);

    /**
     * constructor to initialize access and secret key
     */
    public BDRController() {

        service = new BDRService();
        env = System.getenv();
        accesskey = env.get("ACCESS_KEY");
        secretkey = env.get("SECRET_KEY");

    }

    /**
     * function to upload file in BDR
     * @param minioUrl
     * @param bucketName
     * @param filePath
     */
    public void uploadToBDR(String minioUrl,String bucketName,String filePath) {

        MinioClient minioClient = service.getMinioClient(minioUrl, accesskey, secretkey);

        try {
            if (!service.searchBucket(minioClient, bucketName) ) {
              service.createBucket(minioClient, bucketName);
            } else {
              System.out.println("Bucket '"+bucketName+"' already exists.");
            }
            uploadFiles(minioClient, bucketName, filePath);
        } catch (Exception e) {
            logger.error("Error : {}", e.getMessage());
        }
    }

    /**
     * function to upload file or all files under the directory
     * @param minioClient
     * @param bucketName
     * @param filePath
     */
    private void uploadFiles(MinioClient minioClient,String bucketName,String filePath) {
        try {
            File file = new File(filePath);
            if(file.isFile()) {
                String objectName = file.getName();
                    service.fileUpload(minioClient,bucketName,objectName,file);
            }else if(file.isDirectory()){
                for (File filePaths : file.listFiles() ) {
                    if(filePaths.isFile()) {
                        String objectName = filePaths.getName();
                            service.fileUpload(minioClient,bucketName,objectName,filePaths);
                    }
                }
            }else {
                logger.info("{} file is not available",filePath);
            }
        } catch(Exception e) {
            logger.error("Error : {}", e );
        }
    }
    /**
     * function to download from BDR
     * @param minioUrl
     * @param bucketName
     * @param objectName
     */
    public void downloadFromBDR(String minioUrl,String bucketName,String objectName) {
        try {
            if (bucketName != null && objectName != null && minioUrl != null && accesskey != null && secretkey != null ) {
                File file = new File(MINIO_DOWNLOAD_FOLDER);
                file.mkdir();
                final String downloadPath = "./" + MINIO_DOWNLOAD_FOLDER +"/" + objectName;
                final MinioClient  minioClient = service.getMinioClient(minioUrl, accesskey, secretkey);
                boolean isExist = false;
                try {
                    isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                } catch (final IllegalArgumentException e) {
                    logger.error("Bucket name is invalid");
                } catch (final Exception e) {
                    logger.error("Error in Bucket initialisation : {}", e.getMessage());
                }
                if (isExist) {
                    service.downloadFiles(minioClient,bucketName,objectName,downloadPath);
                } else {
                    logger.warn("Bucket doesn't exist");
                }
            }
        } catch (Exception e) {
            logger.error(" Wrong Port no  {}", e.getMessage());
        }
    }

    /**
     * function to auto upload files to BDR
     * @param host
     * @param bucket
     * @param path
     * @param uCount
     */
    public void autoUploadToBDR(String host, String bucket, String path, int uCount) {
        MinioClient minioClient = service.getMinioClient(host, accesskey, secretkey);
        File file = new File(path);
        if(file.isFile()) {
            try {
               if (! service.searchBucket(minioClient, bucket) ) {
                    service.createBucket(minioClient, bucket);
               }
               for (int i=0 ; i<uCount; i++) {
                   String objectName = "FILE";
                   objectName += String.format("11%07d", i);
                   service.fileUpload(minioClient,bucket,objectName,file);
               }
            }  catch (Exception e) {
                logger.info("Error : {} ",e.getMessage());
            }
        } else 
            logger.error("File is not available");
    }

    /**
     * function to auto download files from BDR
     * @param host
     * @param bucket
     * @param uCount
     */
    public void autoDownloadToBDR(String host, String bucket, int uCount) {
        for(int i=0; i< uCount; i++) {
            String objectName = "FILE";
            objectName += String.format("11%07d", i);
            downloadFromBDR(host, bucket, objectName);
        }
    }

    /**
     * List all bucket in minio server
     * @param url
     */
    public void listBuckets(String url) {
        MinioClient minioClient = service.getMinioClient(url,accesskey,secretkey);
        List<Bucket>  bucketList = service.listBuckets(minioClient);
        if (bucketList != null) {
            for (Bucket bucket : bucketList) {
                System.out.println(bucket.creationDate() + ", " + bucket.name());
              }
        }
    }

    /**
     * 
     * Function to search bucket in minio
     * 
     * @param url
     * @param bucketName
     */
    public void searchBucket(String url, String bucketName) {
        MinioClient minioClient = service.getMinioClient(url,accesskey,secretkey);
        boolean status = service.searchBucket(minioClient,bucketName);
        if(status) {
            logger.info("Bucket {} is exist",bucketName);
        }else {
            logger.error("Bucket {} not found",bucketName);
        }
    }

    /**
     * function to create bucket
     * @param url
     * @param bucketName
     */
    public void createBucket(String url, String bucketName) {
        MinioClient minioClient = service.getMinioClient(url,accesskey,secretkey);
        if(service.createBucket(minioClient,bucketName)) {
            logger.info("Bucket {} created successfully", bucketName);
        } else {
            logger.error("Something went wrong");
        }
    }

    /**
     * 
     * function to delete bucket
     * 
     * @param url
     * @param bucketName
     */
    public void deleteBucket(String url, String bucketName) {
        MinioClient minioClient = service.getMinioClient(url,accesskey,secretkey);
        if(service.deleteBucket(minioClient,bucketName)) {
            logger.info("Bucket {} delete successfully", bucketName);
        } else {
            logger.error("Something went wrong");
        }
    }

    /**
     * 
     * List all objects from a particular bucket
     * @param url
     * @param bucketName
     */
    public void listAllObjects(String url, String bucketName) {
        MinioClient minioClient = service.getMinioClient(url,accesskey,secretkey);
        Iterable<Result<Item>> results = service.listAllObjects(minioClient,bucketName);
        if(results!=null) {
            for (Result<Item> result : results) {
                try {
                    logger.info("{}",result.get().objectName());
                } catch (InvalidKeyException | ErrorResponseException | IllegalArgumentException
                        | InsufficientDataException | InternalException | InvalidBucketNameException
                        | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                        | IOException e) {
                    logger.error("Error : {}",e.getMessage());
                }
            }
        }else {
            logger.error("Something went wrong");
        }
    }

    /**
     * function to search object in bucket
     * @param url
     * @param bucketName
     * @param objectName
     */
    public void searchObjectInBucket(String url, String bucketName, String objectName) {
        MinioClient minioClient = service.getMinioClient(url,accesskey,secretkey);
        Iterable<Result<Item>> results = service.listAllObjects(minioClient,bucketName);
        if(results!=null) {
            boolean found = false;
            for (Result<Item> result : results) {
                try {
                    if(result.get().objectName().equals(objectName)) {
                        logger.info("Object exists");
                        found = true;
                        break;
                    }
                } catch (InvalidKeyException | ErrorResponseException | IllegalArgumentException
                        | InsufficientDataException | InternalException | InvalidBucketNameException
                        | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                        | IOException e) {
                    logger.error("Error : {} ",e.getMessage());
                }
            }
            if ( !found) {
                logger.error("Object not found");
            }
        }
    }
}
