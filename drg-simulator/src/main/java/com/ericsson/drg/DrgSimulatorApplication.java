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
package com.ericsson.drg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ericsson.drg.constants.Constants;
import com.ericsson.drg.helper.BDROperations;
import com.ericsson.drg.helper.DataProviderOperations;
import com.ericsson.drg.helper.DataSpaceOperations;
import com.ericsson.drg.helper.DmaapKafkaMessages;
import com.ericsson.drg.helper.FileFormatInteractiveOperation;
import com.ericsson.drg.helper.FileFormatOperations;
import com.ericsson.drg.helper.FileFormatAutomationOperation;
import com.ericsson.drg.helper.KafkaInteractiveOperationsHelper;
import com.ericsson.drg.helper.KafkaOperationsHelper;
import com.ericsson.drg.helper.MessageBusOperation;
import com.ericsson.drg.helper.MinioOperationsHelper;
import com.ericsson.drg.helper.NotificationTopicOperations;
import com.ericsson.drg.helper.SFTPOperationsHelper;
import com.ericsson.drg.model.Metadata;
import com.ericsson.drg.utility.DRGUtility;

//@SpringBootApplication
public class DrgSimulatorApplication implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(DrgSimulatorApplication.class);
    private static final List<KafkaOperationsHelper> activeDrgThreadList = new ArrayList<>();
   
    /**
     * Selection Menu Function
     *
     * @param selection no.
     */
    private int selectionMenu(final int selection) {
        int flag = 0;
        Scanner scanner = new Scanner(System.in);
        switch (selection) {
        case 1:
            this.displayMetaData(DRGUtility.getMetadata());
            break;
        case 2:
            this.listenKafkaNotificationInteractiveMode();
            break;
        case 3:
            this.listenDmaapNotification();
            break;
        case 4:
            System.out.print("Enter Bucket Name : ");
            String bucketName = scanner.next().trim();
            System.out.print("Enter Object Name : ");
            String objectName = scanner.next().trim();
            System.out.print("Enter Access End Point : ");
            String  accessEndpoint = scanner.next().trim();  
            final MinioOperationsHelper minioOperationsHelper = new MinioOperationsHelper(bucketName, objectName,accessEndpoint);
            minioOperationsHelper.downloadObject();
            break;
        case 5:
            System.out.println("Enter Access End Point : ");
            String accessEndPoint = scanner.next().trim();
            System.out.println("Enter File Name : ");
            String fileName = scanner.next().trim();

            final SFTPOperationsHelper sftpOperationsHelper = new SFTPOperationsHelper(accessEndPoint, fileName);
            sftpOperationsHelper.downloadFile();
            break;
        case 6:
            this.displayMetaData(DRGUtility.getMetadata());
            this.listenKafkaNotificationInteractiveMode();
            break;
        case 7:
            flag = 1;
            for (KafkaOperationsHelper t : activeDrgThreadList) {
                logger.info(" {} thread is closed ", t.getTopic());
                t.setFlag(false);
            }
            break;
        default:
            logger.info(" Invalid selection ");
            break;
        }
        return flag;
    }
    
    /**
     * Listen Notifications from Dmaap 
     */
    private void listenDmaapNotification() {
        
        DmaapKafkaMessages dmaapKafkaMessages = new DmaapKafkaMessages();
        
        for(Metadata metadata : DRGUtility.getMetadata()) {
            dmaapKafkaMessages.getNotifications(metadata.getTopicName(), metadata.getAccessEndpoints());
            
        }
        
    }

    /**
     * Display Every Details of MetaData
     * @param list
     */
    private void displayMetaData(final List<Metadata> list) {
        if (list != null && !list.isEmpty()) {
            for (final Metadata metadataItem : list) {

                final String logMessage = metadataItem.toString();
                logger.info(logMessage);

            }
        } else {
            logger.info(" No data to display ");
        }
    }
    
    /**
     * Listen Kafka Notification in Interactive Mode
     */
    private void listenKafkaNotificationInteractiveMode() {
        final Map<String, List<String>> topicToKafkaAddressMap = DRGUtility.getTopicAndAddress();

        for (final String topic : topicToKafkaAddressMap.keySet()) {
            KafkaInteractiveOperationsHelper helper = new KafkaInteractiveOperationsHelper(topic, topicToKafkaAddressMap.get(topic));
            helper.downloadObject();
        }
    }

    /**
     * Listern all Notification and Download Object
     */
    private void listenForKafkaNotifications() {
        final Map<String, List<String>> topicToKafkaAddressMap = DRGUtility.getTopicAndAddress();

        for (final String topic : topicToKafkaAddressMap.keySet()) {

            final KafkaOperationsHelper helper = new KafkaOperationsHelper(topic, topicToKafkaAddressMap.get(topic));
            helper.setName(topic);

            boolean isDrgDiscoveryOngoingForTopic = false;
            for (KafkaOperationsHelper activeThread : activeDrgThreadList) {
                if (activeThread.getTopic().equals(topic)) {
                    isDrgDiscoveryOngoingForTopic = true;
                    break;
                }
            }
            if (!isDrgDiscoveryOngoingForTopic) {
                activeDrgThreadList.add(helper);
                logger.info("New DRG thread started for topic {} ", topic);
                helper.start();
            }

        }
        List<String> topicNameList = new ArrayList<String>(topicToKafkaAddressMap.keySet());
        for (int i = 0; i < activeDrgThreadList.size(); i++) {
            if (!topicNameList.contains(activeDrgThreadList.get(i).getName()) || !activeDrgThreadList.get(i).isFlag()) {
                logger.info(" {} is removed ", activeDrgThreadList.get(i).getName());
                activeDrgThreadList.get(i).setFlag(false);
                activeDrgThreadList.remove(i);
            }
        }

    }

    /**
     * Application will Run in Interactive Mode
     */
    private void runInteractiveMode() {
        logger.info("Executing drgSimulator in interactive mode");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                logger.info(" Select Below Operations");
                logger.info("Select an option: \n" 
                        + "  1) Get metadata\n"
                        + "  2) Get notifications from Kafka and download object\n" 
                        + "  3) Get notifications from Dmaap and download object\n"
                        + "  4) Download object from BDR\n"
                        + "  5) Download file from ENM SFTP\n"
                        + "  6) Get metadata, Get notification and download object\n" 
                        + "  7) Exit\n ");
                final int selection = scanner.nextInt();

                final int flag = this.selectionMenu(selection);
                if (flag == 1) {
                    logger.info(" Exited ");
                    break;
                }
            } catch (Exception e) {
                logger.error(" Number format is wrong {} ", e.getMessage());
            }

        }

        System.exit(0);
    }
  

    /**
     * Run Application in Automation Mode
     * @throws InterruptedException
     */
    private void runAutomationMode() throws InterruptedException {
        logger.info("Executing drgSimulator in automation mode");
        while (true) {
            Thread.sleep(1000);
            DRGUtility.getMetadata();
            listenForKafkaNotifications();            
        }
               
    }
    /**
     * Main Function
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(DrgSimulatorApplication.class, args);
    }

    
    /**
     * Overrides run method for runtime arguments
     * @param runtime argument
     */
    @Override 
    public void run(final String... args) throws Exception {
        DRGUtility.initialize(args);
        if (DRGUtility.getCommandLineParams().size() > 0
                && DRGUtility.getCommandLineParams().containsKey(Constants.INTERACTIVE_MODE)) {
            if (DRGUtility.getCommandLineParams().get(Constants.INTERACTIVE_MODE) != null
                    && (DRGUtility.getCommandLineParams().get(Constants.INTERACTIVE_MODE).toString()).equalsIgnoreCase("true")) {
                runInteractiveMode();
            } else {
                runAutomationMode();
                
            }
        }
        if (DRGUtility.getCommandLineParams().containsKey("i")) {
            runInteractiveMode();
        }
        else {
            runAutomationMode();          
        }

    }
    
   
}
