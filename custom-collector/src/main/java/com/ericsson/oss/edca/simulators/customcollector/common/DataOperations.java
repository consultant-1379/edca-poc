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

package com.ericsson.oss.edca.simulators.customcollector.common;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.oss.edca.simulators.customcollector.config.*;
import com.ericsson.oss.edca.simulators.customcollector.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataOperations {
    private static final Logger log = LoggerFactory.getLogger(DataOperations.class);

    @Autowired
    private BDRConfiguration bdr;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TestConfiguration test;

    @Autowired
    private KafkaConfiguration kafka;

    @Autowired
    private SFTPConfiguration sftp;

    /**
     * Generate a generic Kafka Notification object from provided Metadata.
     *
     * @param objectName
     *            name of the object
     * @param notificationData
     *            metadata to form the notification event
     * @param endpointInterface
     * @return notification object
     */

    public Notification createNotification(String objectName, NotificationData notificationData, String endpointInterface) {
        Notification notification = new Notification(notificationData);
        notification.setEndpointInterface(endpointInterface);
        notification.setObjectname(Collections.singletonList(objectName));
        notification.setDataEncoding(getExtensionOfFile(objectName));
        if (endpointInterface.equalsIgnoreCase(Constants.MINIO)) {
            notification.setFilePath(bdr.getBucket());
            notification.setAccessEndpoint(bdr.getBDRAccessEndpoint());
        } else if (endpointInterface.equalsIgnoreCase(Constants.SFTP)) {
            notification.setAccessEndpoint(sftp.getSFTPAccessEndpoint());
        }
        return notification;
    }

    /**
     * Generate a generic Kafka Notification object from provided V2 Model data.
     *
     * @param objectName
     *            name of the object
     * @param metadata
     *            metadata to form the notification event
     * @param endpointInterface
     * @return notification object
     */

    /*
     * public Notification createNotification(String objectName, BulkDataRepository bulkDataRepository, String endpointInterface) { Notification notification = new Notification(bulkDataRepository);
     * notification.setEndpointInterface(endpointInterface); notification.setObjectname(Collections.singletonList(objectName)); notification.setDataEncoding(getExtensionOfFile(objectName)); if
     * (endpointInterface.equalsIgnoreCase(Constants.MINIO)) { notification.setBucketname(bdr.getBucket()); notification.setAccessEndpoint(bdr.getBDRAccessEndpoint()); } else if
     * (endpointInterface.equalsIgnoreCase(Constants.SFTP)) { notification.setAccessEndpoint(sftp.getSFTPAccessEndpoint()); } return notification; }
     */

    ///
    /**
     * Generate BulkDataRepository POJO from file.
     *
     * @param filePath
     * @return
     */
    public BulkDataRepository createBDRInfoFromFile(String filePath) {
        BulkDataRepository bulkDataRepository = null;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                bulkDataRepository = mapper.readValue(Files.readAllBytes(path), BulkDataRepository.class);

            } else {
                throw new FileNotFoundException(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bulkDataRepository;
    }

    public MessageBus createMessageBusInfoFromFile(String filePath) {
        MessageBus messageBus = null;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                messageBus = mapper.readValue(Files.readAllBytes(path), MessageBus.class);

            } else {
                throw new FileNotFoundException(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageBus;
    }

    public NotificationTopic createNotificationTopicInfoFromFile(String filePath) {
        NotificationTopic notificationTopic = null;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                notificationTopic = mapper.readValue(Files.readAllBytes(path), NotificationTopic.class);
            } else {
                throw new FileNotFoundException(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notificationTopic;
    }

    public DataSpace createDataspaceInfoFromFile(String filePath) {
        DataSpace dataSpace = null;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                dataSpace = mapper.readValue(Files.readAllBytes(path), DataSpace.class);

            } else {
                throw new FileNotFoundException(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSpace;
    }

    public DataCollector createDataCollectorInfoFromFile(String filePath) {
        DataCollector dataCollector = null;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                dataCollector = mapper.readValue(Files.readAllBytes(path), DataCollector.class);
            } else {
                throw new FileNotFoundException(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataCollector;
    }

    public DataProviderType createDataProviderTypeInfoFromFile(String filePath) {
        DataProviderType dataProviderType = null;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                dataProviderType = mapper.readValue(Files.readAllBytes(path), DataProviderType.class);
            } else {
                throw new FileNotFoundException(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataProviderType;
    }

    public FileFormat createFileFormatInfoFromFile(String filePath) {

        FileFormat fileFormat = null;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                fileFormat = mapper.readValue(Files.readAllBytes(path), FileFormat.class);
            } else {
                throw new FileNotFoundException(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileFormat;
    }

    ////
    /*  */

    /**
     * Generate Metadata POJO from file.
     *
     * @param filePath
     * @return
     */

    public NotificationData createNotificationdataFromFile(String filePath) {
        NotificationData notificationData = null;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                notificationData = mapper.readValue(Files.readAllBytes(path), NotificationData.class);
                //metadata.setTopicName(kafka.getTopic());
                //metadata.setAccessEndpoints(new HashSet<>(Arrays.asList(kafka.getBootstrapUrl().split(","))));
                log.info("NotificationData accessEndpoint={}", notificationData.getAccessEndpoints());
            } else {
                throw new FileNotFoundException(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notificationData;
    }

    /**
     * This method is for future use. Fetch all the files from the specified directory as per the extension.
     *
     * @param dataDir
     * @return
     * @throws FileNotFoundException
     */

    public List<String> getListOfFilesFromDataDirectory(String dataDir) throws FileNotFoundException {
        Path dataDirPath = Paths.get(dataDir);
        if (Files.isDirectory(dataDirPath)) {
            try (Stream<Path> list = Files.list(dataDirPath)) {
                return list.map(Path::toString).filter(it -> it.endsWith(".xml")).collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new FileNotFoundException(dataDir + " does not point to a valid directory");
    }

    /**
     * Create multiple files from a sample file.
     * 
     * @param sampleFile
     * @return
     */
    public List<String> createDataFilesFromSample(String sampleFile) {
        List<String> listOfFiles = new ArrayList<>();
        try {
            Path tempDir = Paths.get(test.getTempDir());
            byte[] bytes = Files.readAllBytes(Paths.get(sampleFile));
            String extension = getExtensionOfFile(sampleFile);
            if (Files.exists(tempDir)) {
                Arrays.stream(Objects.requireNonNull(new File(test.getTempDir()).listFiles())).forEach(File::delete);
                Files.deleteIfExists(tempDir);
            }
            Path directory = Files.createDirectory(tempDir);
            for (int i = 0; i < test.getCounter(); i++) {
                Path tempFile = Files.createTempFile(directory.toAbsolutePath(), String.valueOf(System.currentTimeMillis()), extension);
                Path write = Files.write(tempFile, bytes);
                listOfFiles.add(write.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfFiles;
    }

    /**
     * This method return the extension of a file.
     * 
     * @param sampleFile
     * @return
     */
    private String getExtensionOfFile(String sampleFile) {
        String extension = "";
        int index = sampleFile.lastIndexOf('.');
        if (index > 0) {
            extension = sampleFile.substring(index);
        }
        return extension;
    }

    /**
     * Return the fileName from the filePath.
     * 
     * @param filePath
     * @return
     */
    public String getFileNameFromPath(String filePath) {
        try {
            return Paths.get(filePath).toFile().getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
