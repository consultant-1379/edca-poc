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

package com.ericsson.oss.edca.simulators.customcollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "edca.test")
public class TestConfiguration {
    private String endpointInterface;
    private String notificationDataFile;
    private String bulkDataRepositoryFile;
    private String messageBusFile;
    private String notificationTopicFile;

    private String dataCollectorFile;
    private String fileFormatFile;
    private String dataProviderTypeFile;

    private String dataSpaceFile;

    private String dataFile;
    private String sftpPath;
    private int counter;
    private String tempDir = "test-data/tmp/";

    public String getEndpointInterface() {
        return endpointInterface;
    }

    public void setEndpointInterface(String endpointInterface) {
        this.endpointInterface = endpointInterface;
    }

    public String getBulkDataRepositoryFile() {
        return bulkDataRepositoryFile;
    }

    public void setBulkDataRepositoryFile(String bulkDataRepostoryFile) {
        this.bulkDataRepositoryFile = bulkDataRepostoryFile;
    }

   

    public String getNotificationDataFile() {
		return notificationDataFile;
	}

	public void setNotificationDataFile(String notificationDataFile) {
		this.notificationDataFile = notificationDataFile;
	}

	public String getDataProviderTypeFile() {
        return dataProviderTypeFile;
    }

    public void setDataProviderTypeFile(String dataProviderTypeFile) {
        this.dataProviderTypeFile = dataProviderTypeFile;
    }

    public String getMessageBusFile() {
        return messageBusFile;
    }

    public void setMessageBusFile(String messageBusFile) {
        this.messageBusFile = messageBusFile;
    }

    public String getNotificationTopicFile() {
        return notificationTopicFile;
    }

    public void setNotificationTopicFile(String notificationTopicFile) {
        this.notificationTopicFile = notificationTopicFile;
    }

    public String getDataCollectorFile() {
        return dataCollectorFile;
    }

    public void setDataCollectorFile(String dataCollectorFile) {
        this.dataCollectorFile = dataCollectorFile;
    }

    public String getFileFormatFile() {
        return fileFormatFile;
    }

    public void setFileFormatFile(String fileFormatFile) {
        this.fileFormatFile = fileFormatFile;
    }

    public String getDataSpaceFile() {
        return dataSpaceFile;
    }

    public void setDataSpaceFile(String dataSpaceFile) {
        this.dataSpaceFile = dataSpaceFile;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public String getSftpPath() {
        return sftpPath;
    }

    public void setSftpPath(String sftpPath) {
        this.sftpPath = sftpPath;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

}
