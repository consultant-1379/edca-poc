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

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.oss.edca.simulators.customcollector.common.*;
import com.ericsson.oss.edca.simulators.customcollector.domain.*;
import com.ericsson.oss.edca.simulators.customcollector.service.CatalogService;
import com.ericsson.oss.edca.simulators.customcollector.service.ExecutorService;

@Component
public class MenuHandler {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private DataOperations operations;
    @Autowired
    private Scanner input;
    @Autowired
    private CatalogService cserv;

    /**
     * Start Execution of Custom Collector in INTERACTIVE mode.
     * 
     * @throws Exception
     */
    public void startExecution() throws Exception {
        String choice;
        do {
            System.out.println(MenuConstants.MENU.toString());
            choice = readSelection();
            executeSelection(choice);
        } while (!choice.equals(String.valueOf(0)));
    }

    /**
     * Method to get the input from User.
     * 
     * @return
     */
    private String readSelection() {
        System.out.print("Enter Choice: ");
        String choice = input.nextLine();
        return choice;
    }

    /**
     * Method to execute the usecase as per User's input.
     * 
     * @param choice
     * @throws Exception
     */
    private void executeSelection(String choice) throws Exception {
        final int action = Integer.parseInt(choice);
        String filePath;
        switch (action) {
        case 1:
            filePath = executorService.getAbsFilePathForCatalog();
            BulkDataRepository bulkDataRepository = operations.createBDRInfoFromFile(filePath);
            executorService.BDRInfoUpload(bulkDataRepository);
            break;
        case 2:
            filePath = executorService.getAbsFilePathForCatalog();
            MessageBus messageBus = operations.createMessageBusInfoFromFile(filePath);
            executorService.messageBusInfoUpload(messageBus);
            break;
        case 3:
            filePath = executorService.getAbsFilePathForCatalog();
            NotificationTopic notificationTopic = operations.createNotificationTopicInfoFromFile(filePath);
            executorService.notificationTopicUpload(notificationTopic);
            break;
        case 4:
            filePath = executorService.getAbsFilePathForCatalog();
            DataSpace dataSpace = operations.createDataspaceInfoFromFile(filePath);
            executorService.dataSpaceUpload(dataSpace);
            break;
        case 5:
            filePath = executorService.getAbsFilePathForCatalog();
            DataCollector dataCollector = operations.createDataCollectorInfoFromFile(filePath);
            executorService.dataCollectorUpload(dataCollector);
            break;
        case 6:
            filePath = executorService.getAbsFilePathForCatalog();
            DataProviderType dataProviderType = operations.createDataProviderTypeInfoFromFile(filePath);
            executorService.dataProviderTypeUpload(dataProviderType);
            break;
        case 7:
            filePath = executorService.getAbsFilePathForCatalog();
            FileFormat fileFormat = operations.createFileFormatInfoFromFile(filePath);
            executorService.fileFormatUpload(fileFormat);
            break;
        case 8:
            executorService.dataFileUpload(executorService.getFilePath(Constants.MINIO));
            break;
        case 9:
            executorService.notificationUpload(executorService.getAbsFilePathForConfig(), executorService.getFilePath(Constants.MINIO), Constants.MINIO);
            break;
        case 10:
            executorService.semiAutomated(Constants.MINIO);
            break;
        case 11:
            executorService.notificationUpload(executorService.getAbsFilePathForConfig(), executorService.getFilePath(Constants.SFTP), Constants.SFTP);
            break;
        case 12:
            executorService.semiAutomated(Constants.SFTP);
            break;

        //case 5: executorService.semiAutomated(Constants.MINIO); break; 

        /*
         * case 13: executorService.semiAutomated(Constants.SFTP); break;
         * 
         * case 11: executorService.semiAutomated(Constants.MINIO); break;
         */

        /*
         * case 2: executorService.dataFileUpload(executorService.getFilePath(Constants.MINIO)); break;
         */
        /*
         * case 3: executorService.notificationUpload(executorService.getAbsFilePathForCatalog() , executorService.getFilePath(Constants.MINIO), Constants.MINIO); break;
         */
        /*
         * case 4: executorService.notificationUpload(executorService.getAbsFilePathForCatalog() , executorService.getFilePath(Constants.SFTP), Constants.SFTP); break;
         */
        /*
         * case 5: executorService.semiAutomated(Constants.MINIO); break;
         * 
         * case 6: executorService.semiAutomated(Constants.SFTP); break;
         */

        case 0:
            System.exit(1);
            break;
        default:
            System.out.println("Wrong Input");
        }
    }

}
