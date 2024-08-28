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

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.oss.edca.simulators.customcollector.common.Constants;
import com.ericsson.oss.edca.simulators.customcollector.common.DataOperations;
import com.ericsson.oss.edca.simulators.customcollector.domain.*;

/**
 * ExecutorService is a one-stop entry-point for all the custom collector use-cases.
 */
@Component
public class ExecutorService {
    @Autowired
    private DataOperations operations;
    @Autowired
    private CatalogService catalogService;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private BDRService bdrService;

    @Autowired
    private Scanner input;
    @Autowired
    private SFTPService sftpService;

    /**
     * Method to upload metadata, upload files to BDR and upload the notification to kafka.
     * 
     * @param endpointInterface
     * @throws Exception
     * 
     **/
    public void semiAutomated(String endpointInterface) throws Exception {
        String dataFilePathForCatalog = getAbsFilePathForConfig();
        String filePath = getFilePath(endpointInterface);

        if (endpointInterface.equalsIgnoreCase(Constants.MINIO)) {
            String fileName = operations.getFileNameFromPath(filePath);
            dataFileUpload(filePath);

            notificationUpload(dataFilePathForCatalog, filePath, endpointInterface);

        } else if (endpointInterface.equalsIgnoreCase(Constants.SFTP)) {
            List<String> files = sftpService.sftpDownload(filePath);
            for (String file : files) {
                String fileName = operations.getFileNameFromPath(file);
                notificationUpload(dataFilePathForCatalog, filePath, endpointInterface);
            }
        }
    }

    public boolean metadataUpload(String filePath) {
        NotificationData data = operations.createNotificationdataFromFile(filePath);
        return catalogService.uploadNotificationData(data);
    }

    public boolean metadataUpload(NotificationData notificationData) {
        return catalogService.uploadNotificationData(notificationData);
    }

    /**
     * Method to upload BulkDataRepository.
     * 
     * @param BulkDataRepository
     * @return
     */
    public int BDRInfoUpload(BulkDataRepository bulkDataRepository) {
        return catalogService.registerBDRInformation(bulkDataRepository);
    }

    /**
     * Method to upload messageBus.
     * 
     * @param messageBus
     * @return
     */
    public int messageBusInfoUpload(MessageBus messageBus) {
        return catalogService.registerMessageBus(messageBus);

    }

    /**
     * Method to upload NotificationTopic.
     * 
     * @param NotificationTopic
     * @return
     */
    public int notificationTopicUpload(NotificationTopic notificationTopic) {
        return catalogService.registerNotificationTopic(notificationTopic);

    }

    /**
     * Method to upload DataCollector.
     * 
     * @param DataCollector
     * @return
     */
    public boolean dataCollectorUpload(DataCollector dataCollector) {
        return catalogService.registerDataCollector(dataCollector);

    }

    /**
     * Method to upload DataProviderType.
     * 
     * @param DataProviderType
     * @return
     */
    public int dataProviderTypeUpload(DataProviderType dataProviderType) {
        return catalogService.registerDataProviderType(dataProviderType);

    }

    /**
     * Method to upload FileFormat.
     * 
     * @param FileFormat
     * @return
     */
    public boolean fileFormatUpload(FileFormat fileFormat) {
        return catalogService.registerFileFormat(fileFormat);

    }

    /**
     * Method to upload dataspace.
     * 
     * @param dataSpace
     * @return
     */
    public int dataSpaceUpload(DataSpace dataSpace) {
        return catalogService.registerDataSpace(dataSpace);

    }

    /**
     * Method to upload notification to kafka.
     * 
     * @param filePathForCatalog
     * @param filePath
     * @param endpointInterface
     */

    public void notificationUpload(String filePathForCatalog, String filePath, String endpointInterface) {

        NotificationData notificationData = operations.createNotificationdataFromFile(filePathForCatalog);

        Notification notification = null;
        if (endpointInterface.equalsIgnoreCase(Constants.MINIO)) {
            String objectName = operations.getFileNameFromPath(filePath);
            notification = operations.createNotification(objectName, notificationData, endpointInterface);
        } else if (endpointInterface.equalsIgnoreCase(Constants.SFTP)) {
            List<String> fileNames = sftpService.sftpDownload(filePath);
            for (String fileName : fileNames) {
                notification = operations.createNotification(fileName, notificationData, endpointInterface);
            }
        }
        notificationUpload(notification, notificationData);
    }

    /**
     * Method to upload notification to kafka.
     * 
     * @param objectName
     * @param notificationData
     * @param endpointInterface
     */

    public void notificationUpload(String objectName, NotificationData notificationData, String endpointInterface) {
        notificationUpload(operations.createNotification(objectName, notificationData, endpointInterface), notificationData);
    }

    /**
     * Method to upload notification to kafka.
     * 
     * @param notification
     * @param notificationData
     */

    public void notificationUpload(Notification notification, NotificationData notificationData) {
        kafkaService.sendNotification(notification, notificationData);
    }

    /**
     * Method to upload data file or object into BDR.
     * 
     * @param filePath
     * @return
     * @throws Exception
     */

    public boolean dataFileUpload(String filePath) throws Exception {
        final String fileName = operations.getFileNameFromPath(filePath);
        return bdrService.uploadFileToBDR(fileName, filePath);
    }

    /**
     * Method to upload data file or object into BDR.
     * 
     * @param objectName
     * @param filePath
     * @return
     * @throws Exception
     */

    public boolean dataFileUpload(String objectName, String filePath) throws Exception {
        return bdrService.uploadFileToBDR(objectName, filePath);
    }

    /**
     * Method to get the file path for data from the user.
     * 
     * @return
     */
    public String getAbsFilePathForCatalog() {
        System.out.print("Enter the absolute file path : ");
        return input.nextLine();
    }

    /**
     * Method to get the file path for data from the user.
     * 
     * @return
     */
    public String getAbsFilePathForConfig() {
        System.out.print("Enter the absolute file path of config file to be used for NotificationData : ");
        return input.nextLine();
    }

    /**
     * Method to get the file path for data file from the user.
     * 
     * @param endpointInterface
     * @return
     */
    public String getFilePath(String endpointInterface) {
        if (endpointInterface.equalsIgnoreCase(Constants.MINIO)) {
            System.out.print("Enter the absolute file path of file to be uploaded: ");
        } else if (endpointInterface.equalsIgnoreCase(Constants.SFTP)) {
            System.out.print("Enter the directory path where files are available: ");
        }
        return input.nextLine();
    }
}
