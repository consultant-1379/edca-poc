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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.oss.edca.simulators.customcollector.common.Constants;
import com.ericsson.oss.edca.simulators.customcollector.common.DataOperations;
import com.ericsson.oss.edca.simulators.customcollector.config.TestConfiguration;
import com.ericsson.oss.edca.simulators.customcollector.domain.*;
import com.ericsson.oss.edca.simulators.customcollector.service.ExecutorService;
import com.ericsson.oss.edca.simulators.customcollector.service.SFTPService;

@Component
public class AutomationHandler {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private DataOperations operations;
    @Autowired
    private TestConfiguration test;
    @Autowired
    private SFTPService sftpService;

    /**
     * start execution of Custom collector in AUTOMATION mode.
     * 
     * @param endpointInterface
     */
    public void startExecution(String endpointInterface) {
        try {

            BulkDataRepository bulkDataRepository = operations.createBDRInfoFromFile(test.getBulkDataRepositoryFile());
            MessageBus messageBus = operations.createMessageBusInfoFromFile(test.getMessageBusFile());
            NotificationTopic notificationTopic = operations.createNotificationTopicInfoFromFile(test.getNotificationTopicFile());
            FileFormat fileFormat = operations.createFileFormatInfoFromFile(test.getFileFormatFile());
            DataCollector dataCollector = operations.createDataCollectorInfoFromFile(test.getDataCollectorFile());
            DataProviderType dataProviderType = operations.createDataProviderTypeInfoFromFile(test.getDataProviderTypeFile());
            DataSpace dataSpace = operations.createDataspaceInfoFromFile(test.getDataSpaceFile());
            NotificationData meta = operations.createNotificationdataFromFile(test.getNotificationDataFile());

            //executorService.metadataUpload(meta);
            /*
             * executorService.BDRInfoUpload(bulkDataRepository); executorService.messageBusInfoUpload(messageBus); executorService.notificationTopicUpload(notificationTopic);
             * executorService.fileFormatUpload(fileFormat); executorService.dataCollectorUpload(dataCollector); executorService.dataProviderTypeUpload(dataProviderType);
             * executorService.DataSpaceInfo(dataSpace);
             */

            final NotificationData notificationData = operations.createNotificationdataFromFile(test.getNotificationDataFile());

            List<String> files = null;
            if (endpointInterface.equalsIgnoreCase(Constants.MINIO)) {

                files = operations.createDataFilesFromSample(test.getDataFile());

            } else if (endpointInterface.equalsIgnoreCase(Constants.SFTP)) {
                files = sftpService.sftpDownload(null);
            }

            int bulkDataRepositoryId = executorService.BDRInfoUpload(bulkDataRepository);

            int messageBusId = executorService.messageBusInfoUpload(messageBus);

            if (messageBusId > 0) {
                notificationTopic.setMessageBusId(messageBusId);
            }
            int notificationTopicId = executorService.notificationTopicUpload(notificationTopic);

            int dataSpaceId = executorService.dataSpaceUpload(dataSpace);

            executorService.dataCollectorUpload(dataCollector);

            if (dataSpaceId > 0) {
                dataProviderType.setDataSpaceId(dataSpaceId);
            }
            int dataProviderTypeId = executorService.dataProviderTypeUpload(dataProviderType);

            if (dataProviderTypeId > 0 && bulkDataRepositoryId > 0 && notificationTopicId > 0) {
                fileFormat.setBulkDataRepositoryId(bulkDataRepositoryId);
                fileFormat.setDataProviderTypeId(dataProviderTypeId);
                fileFormat.setNotificationTopicId(notificationTopicId);
                fileFormat.setDataCollectorId(dataCollector.getCollectorId());
            }
            executorService.fileFormatUpload(fileFormat);

            for (String file : files) {
                String objectName = operations.getFileNameFromPath(file);
                if (endpointInterface.equalsIgnoreCase(Constants.MINIO)) {
                    executorService.dataFileUpload(objectName, file);
                }
                executorService.notificationUpload(objectName, notificationData, endpointInterface);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
