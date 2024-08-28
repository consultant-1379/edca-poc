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

package com.ericsson.minio.service;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import io.minio.BucketExistsArgs;
import io.minio.DownloadObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.Result;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.RegionConflictException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

@Service
public class BDRService {

    private static Logger logger = LoggerFactory.getLogger(BDRService.class);
    public BDRService() {
    }

    /**
     * function to upload files to BDR
     * @param minioClient
     * @param bucketName
     * @param objectName
     * @param file
     * @throws InvalidKeyException
     * @throws ErrorResponseException
     * @throws InsufficientDataException
     * @throws InternalException
     * @throws InvalidResponseException
     * @throws NoSuchAlgorithmException
     * @throws ServerException
     * @throws XmlParserException
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public void fileUpload(MinioClient minioClient, String bucketName, String objectName, File file){
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder().
                        bucket(bucketName).
                        object(objectName).
                        filename(file.toString()).build());
            logger.info("File {} is uploaded successfully",objectName);
        } catch (Exception e) {
            logger.error("Error : {}",e);
        }
    }

    /**
     * function to download files from BDR
     * @param minioClient
     * @param bucketName
     * @param objectName
     * @param downloadPath
     */
    public void downloadFiles(MinioClient minioClient, String bucketName, String objectName, String downloadPath) {
        try {
            minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).filename(downloadPath).build());
            logger.info("{} is downloaded Successfully", objectName);
        } catch (final Exception e) {
            logger.error("{} does not exist in BDR", objectName);
        }
    }

    /**
     * List all buckets
     * @param minioClient
     * @return
     */
    public List<Bucket> listBuckets(MinioClient minioClient) {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            logger.error("Error : {} ",e.getMessage());
        }
        return null;
    }

    /**
     * function to search bucket
     * @param minioClient
     * @param bucketName
     * @return
     */
    public boolean searchBucket(MinioClient minioClient, String bucketName) {
        boolean status = false;
        try {
            status = minioClient.bucketExists(  BucketExistsArgs.builder().bucket(bucketName).build() );
        }  catch (Exception e) {
            logger.error("Error : {} ",e.getMessage());
        }
        return status;
    }

    /**
     * function to create bucket
     * @param minioClient
     * @param bucketName
     * @return
     */
    public boolean createBucket(MinioClient minioClient, String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            return true;
        } catch (InvalidKeyException | ErrorResponseException | IllegalArgumentException | InsufficientDataException
                | InternalException | InvalidBucketNameException | InvalidResponseException | NoSuchAlgorithmException
                | RegionConflictException | ServerException | XmlParserException | IOException e) {
            logger.error("Error : {}",e.getMessage());
        }
        return false;
    }

    /**
     * function to delete bucket
     * @param minioClient
     * @param bucketName
     * @return
     */
    public boolean deleteBucket(MinioClient minioClient, String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            return true;
        } catch (InvalidKeyException | ErrorResponseException | IllegalArgumentException | InsufficientDataException
                | InternalException | InvalidBucketNameException | InvalidResponseException | NoSuchAlgorithmException
                | ServerException | XmlParserException | IOException e) {
            logger.error("Error : {}",e.getMessage());
        }
        return false;
    }
    /**
     * returns minioClient
     * @param url
     * @param accesskey
     * @param secretkey
     * @return
     */
    public MinioClient getMinioClient(String url, String accesskey, String secretkey) {
        MinioClient minioClient = MinioClient.builder().endpoint(url).credentials(accesskey, secretkey).build();
        return minioClient;
    }

    /**
     * List all object in a bucket 
     * @param minioClient
     * @param bucketName
     * @return
     */
    public Iterable<Result<Item>> listAllObjects(MinioClient minioClient,String bucketName) {
        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
    }
}
