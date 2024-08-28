package com.ericsson.drg;

import java.util.Arrays;
import java.util.HashSet;
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
import com.ericsson.drg.helper.FileFormatAutomationOperation;
import com.ericsson.drg.helper.FileFormatInteractiveOperation;
import com.ericsson.drg.helper.FileFormatOperations;
import com.ericsson.drg.helper.KafkaInteractiveOperationsHelper;
import com.ericsson.drg.helper.MessageBusOperation;
import com.ericsson.drg.helper.MinioOperationsHelper;
import com.ericsson.drg.helper.NotificationTopicOperations;
import com.ericsson.drg.helper.SFTPOperationsHelper;
import com.ericsson.drg.utility.DRGUtility;

@SpringBootApplication
public class DrgSimulatorApplicationV2  implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(DrgSimulatorApplicationV2.class);
    /**
     * Main Function
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DrgSimulatorApplicationV2.class, args);
    }
    
    
    /**
     * Application will Run in Interactive Mode
     */
    private void runInteractiveModeV2() {
        logger.info("Executing drgSimulator in interactive mode :");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                logger.info(" Select Below Operations");
                logger.info("Select an option: \n" 
                        + "  1)  Discover Bulk Data Repository\n"
                        + "  2)  Discover Message Bus\n"
                        + "  3)  Discover Notification Topic\n"
                        + "  4)  Discover Dataspace\n" 
                        + "  5)  Discover Data Provider Type\n"
                        + "  6)  Discover File Format\n"                       
                        + "  7)  Get Message from DMaap and download files\n"
                        + "  8)  Get Message from Kafka and download files\n"
                        + "  9)  Download from BDR\n"
                        + "  10) Discover File Format,Subscribe Kafka,Consume messages,Download files\n"
                        + "  11) Download file from ENM SFTP\n"
                        + "  12) Exit\n");
                final int selection = scanner.nextInt();

                final int flag = this.selectionMenuV2(selection);
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
     * Selection Menu Version 2 Drg :)
     * @param selection
     * @return
     */
    private int selectionMenuV2(int selection) {
        
        int flag = 0;
        Scanner in = new Scanner(System.in);
        try {
        switch(selection) {
        
        case 1:
            BDROperations.getBDR();
            break;
        case 2:
            MessageBusOperation.getMessageBus();
            break;
        case 3:
            NotificationTopicOperations.getNotificationTopic();
            break;
        case 4:
            DataSpaceOperations.getDataSpace();
            break;
        case 5:
            DataProviderOperations.getDataProvider();
            break;
        case 6:
            FileFormatOperations.getFileFormat();
            break;      
        case 7:
            System.out.print("Enter url : ");
            String url = in.next();
            System.out.print("Enter topic name : ");
            String topic = in.next();
            HashSet set =new HashSet();
            set.add(url);
            logger.info("Entered Url : {} and topic : {} ", url , topic);
            new DmaapKafkaMessages().getNotifications(topic,set);
            break;
        case 8:
            System.out.print("Enter url : ");
            String bootstrapUrl = in.next();
            System.out.print("Enter topic name : ");
            String topicName = in.next();
            logger.info("Entered Url : {} and topic : {} ", bootstrapUrl, topicName);
            KafkaInteractiveOperationsHelper kafkaInteractiveOperationsHelper = new KafkaInteractiveOperationsHelper(topicName, Arrays.asList(bootstrapUrl));
            kafkaInteractiveOperationsHelper.downloadObject();
            break;
        case 9:           
            System.out.print("Enter Bucket Name : ");
            String bucketName = in.next().trim();
            System.out.print("Enter Object Name : ");
            String objectName = in.next().trim();
            System.out.print("Enter Access End Point : ");
            String  accessEndpoint = in.next().trim();
            
            final MinioOperationsHelper minioOperationsHelper = new MinioOperationsHelper(bucketName, objectName,accessEndpoint);
            minioOperationsHelper.downloadObject();
            break;
        case 10:
            FileFormatInteractiveOperation.getFileFormate();
            break;
        case 11:
            System.out.println("Enter Access End Point : ");
            String accessEndPoint = in.next().trim();
            System.out.println("Enter File Name : ");
            String fileName = in.next().trim();

            final SFTPOperationsHelper sftpOperationsHelper = new SFTPOperationsHelper(accessEndPoint, fileName);
            sftpOperationsHelper.downloadFile();
            break;
        case 12:
            flag = 1;
            break;
            
        default :
            logger.error("Wrong input");
        
        }
        }catch (Exception e) {
            logger.error("Error : {} ", e.toString());
            e.printStackTrace();
        }
        
        return flag;
    }
    
    /**
     * Run Application in Automation Mode
     * @throws InterruptedException
     */
    private void runAutomationMode() throws InterruptedException {
        logger.info("Executing drgSimulator in automation mode :");       
        FileFormatAutomationOperation.getFileFormate();
        
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
                runInteractiveModeV2();
            } else {
                runAutomationMode();
                
            }
        }
        if (DRGUtility.getCommandLineParams().containsKey("i")) {
            runInteractiveModeV2();
        }
        else {
            runAutomationMode();                        
        }

    }


}
