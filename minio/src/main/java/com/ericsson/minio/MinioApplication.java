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

package com.ericsson.minio;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

import com.ericsson.minio.controller.BDRController;

@SpringBootApplication
public class MinioApplication implements ApplicationRunner {

    private BDRController bdrController;
    private static Logger logger = LoggerFactory.getLogger(MinioApplication.class);
    public MinioApplication() {
        bdrController = new BDRController();
    }

    /**
     * menu selection function
     */
    public void menuSelection() {
        while(true) {
            Scanner in = new Scanner(System.in);
            logger.info("\nSelect an option\n"
                        + "1) Upload file into BDR \n"
                        + "2) Download file from BDR \n"
                        + "3) List buckets\n"
                        + "4) Search bucket\n"
                        + "5) Create bucket\n"
                        + "6) Delete bucket\n"
                        + "7) List All object\n"
                        + "8) Search an object\n"
                        + "9) Exit \n");
            int choice = in.nextInt();
            String url = null;
            String bucketName = null;
            String filePath = null;
            switch(choice) {
            case 1: 
                System.out.print("Enter BDR Url : ");
                url = in.next().trim();
                System.out.print("Enter bucket name : ");
                bucketName = in.next().trim();
                System.out.print("Enter file path to upload : ");
                filePath = in.next().trim();
                this.bdrController.uploadToBDR(url, bucketName, filePath);
                break;
            case 2:
                System.out.print("Enter BDR Url : ");
                url = in.next().trim();
                System.out.print("Enter bucket name : ");
                bucketName = in.next().trim();
                System.out.print("Enter file to download : ");
                String objectName = in.next().trim();
                logger.info("BDR :- URL : {} BucketName : {} ObjectName : {} ",url,bucketName,objectName);
                this.bdrController.downloadFromBDR(url, bucketName, objectName);
                break;
            case 3:
                System.out.print("Enter BDR Url : ");
                url = in.next().trim();
                this.bdrController.listBuckets(url);
                break;
            case 4:
                System.out.print("Enter BDR Url : ");
                url = in.next().trim();
                System.out.print("Enter bucket name : ");
                bucketName = in.next().trim();
                this.bdrController.searchBucket(url,bucketName);
                break;
            case 5:
                System.out.print("Enter BDR Url : ");
                url = in.next().trim();
                System.out.print("Enter bucket name : ");
                bucketName = in.next().trim();
                this.bdrController.createBucket(url,bucketName);
                break;
            case 6:
                System.out.print("Enter BDR Url : ");
                url = in.next().trim();
                System.out.print("Enter bucket name : ");
                bucketName = in.next().trim();
                this.bdrController.deleteBucket(url,bucketName);
                break;
            case 7:
                System.out.print("Enter BDR Url : ");
                url = in.next().trim();
                System.out.print("Enter bucket name : ");
                bucketName = in.next().trim();
                this.bdrController.listAllObjects(url,bucketName);
                break;
            case 8:
                System.out.print("Enter BDR Url : ");
                url = in.next().trim();
                System.out.print("Enter bucket name : ");
                bucketName = in.next().trim();
                System.out.print("Enter object name to search : ");
                objectName = in.next().trim();
                this.bdrController.searchObjectInBucket(url,bucketName,objectName);
            case 9:
                in.close();
                logger.info("\nExited..");
                System.exit(0);
            default:
                logger.info("Wrong choice");
            }
        }
    }
    
    /**
     * main function
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MinioApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args ) {
        MinioApplication app = new MinioApplication();
        if(args.getSourceArgs().length == 0) {
            app.menuSelection();           
        }else if( args.containsOption("f")) {
            app.runAppUsingFile(args);
        }else if(args.containsOption("h")) {
            logger.info("\nUsage : java -jar minioApp [OPTIONS] \n\n\t --f\tUse any yaml file to provide input to minioApp\n\t\t\t for e.g. --f=input.yaml");
        }else if(args.containsOption("a")) {
            app.runAutoMode(args);
        }
    }

    private void runAutoMode(ApplicationArguments args) {
        if(args.getOptionValues("a").isEmpty()) {
            if(args.containsOption("u") ) {
                if(args.containsOption("host") && args.containsOption("bucket") && args.containsOption("path") && args.containsOption("uCount") 
                   && !args.getOptionValues("host").isEmpty() && !args.getOptionValues("bucket").isEmpty()&& !args.getOptionValues("path").isEmpty() && !args.getOptionValues("uCount").isEmpty()  ) {
                    String host = args.getOptionValues("host").get(0);
                    String bucket = args.getOptionValues("bucket").get(0);
                    String path = args.getOptionValues("path").get(0);
                    int uCount = Integer.parseInt(args.getOptionValues("uCount").get(0));
                    bdrController.autoUploadToBDR(host,bucket,path,uCount);
                }
            } else if ( args.containsOption("d") ) {
                if(args.containsOption("host") && args.containsOption("bucket")&& args.containsOption("uCount") 
                        && !args.getOptionValues("host").isEmpty() && !args.getOptionValues("bucket").isEmpty() && !args.getOptionValues("uCount").isEmpty()  ) {
                         String host = args.getOptionValues("host").get(0);
                         String bucket = args.getOptionValues("bucket").get(0);
                         int uCount = Integer.parseInt(args.getOptionValues("uCount").get(0));
                         bdrController.autoDownloadToBDR(host,bucket,uCount);
                     }
            }
        }else {
            logger.error("Run command is inappropriate");
        }
    }

    /**
     * function to run application using yaml file
     * @param args
     */
    private void runAppUsingFile(ApplicationArguments args) {
        String filePath = args.getOptionValues("f").get(0);
        Yaml yaml = new Yaml();
        try {
            if(filePath.endsWith(".yaml") || filePath.endsWith(".yml") ) {
                InputStream inputStream = new FileInputStream(filePath);
                HashMap yamlMap = yaml.load(inputStream);
                System.out.println(yamlMap.get("bdr"));
                ArrayList arrayList = (ArrayList) yamlMap.get("bdr");
                for (Object object : arrayList) {
                    HashMap map = (HashMap) object;
                    System.out.println(map.toString());
                    String url = (String) map.get("url");
                    String bucket = (String) map.get("bucket");
                    ArrayList<String> objectNames = (ArrayList<String>) map.get("objectNames");
                    if(map.get("request").equals("upload")) {
                        for(String file : objectNames) {
                            bdrController.uploadToBDR(url, bucket, file);
                        }
                    }else if(map.get("request").equals("download")) {
                        for(String objectName : objectNames) {
                            bdrController.downloadFromBDR(url, bucket, objectName);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Error : {}",e.getMessage());
        }
    }
}
