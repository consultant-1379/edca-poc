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

package com.ericsson.oss.edca.simulators.customcollector.domain;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Notification", description = "Kafka Message Schema")
public class Notification {
    private String accessEndpoint;
    private String filePath;
    private List<String> objectname;
    private String dataSubCategory;
    private String sourceType;
    private String dataEncoding;
    private String dataSpecRef;
    private String endpointInterface;

    public Notification(NotificationData notificationData) {
        this.dataSubCategory = notificationData.getDataSubCategory().stream().findFirst().get();
        this.sourceType = notificationData.getSourceType().stream().findFirst().get();
        this.dataSpecRef = notificationData.getTopicSpecRef();
    }

    public Notification(String accessEndpoint, String filePath, List<String> objectname, String dataSubCategory, String sourceType, String dataEncoding, String dataSpecRef, String endpointInterface) {
        this.accessEndpoint = accessEndpoint;
        this.filePath = filePath;
        this.objectname = objectname;
        this.dataSubCategory = dataSubCategory;
        this.sourceType = sourceType;
        this.dataEncoding = dataEncoding;
        this.dataSpecRef = dataSpecRef;
        this.endpointInterface = endpointInterface;
    }

    public Notification() {
    }

    @Override
    public String toString() {
        return "Notification [accessEndpoint=" + accessEndpoint + ", filePath=" + filePath + ", dataEncoding=" + dataEncoding + ", dataSpecRef=" + dataSpecRef + ", dataSubCategory=" + dataSubCategory
                + ", endpointInterface=" + endpointInterface + ", objectname=" + objectname + ", sourceType=" + sourceType + "]";
    }

    @ApiModelProperty(notes = "This provides Information where a file can be retrieved using sftp/http/nfs/s3 notation")
    public String getAccessEndpoint() {
        return accessEndpoint;
    }

    public void setAccessEndpoint(String accessEndpoint) {
        this.accessEndpoint = accessEndpoint;
    }

    @ApiModelProperty(notes = "Name of the S3 bucket containing the object")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @ApiModelProperty(notes = "Array of strings. Name of the files or the object to be retrieved")
    public List<String> getObjectname() {
        return objectname;
    }

    public void setObjectname(List<String> objectname) {
        this.objectname = objectname;
    }

    @ApiModelProperty(notes = "Follows FLS notation that is used to filter out a specific type of data")
    public String getDataSubCategory() {
        return dataSubCategory;
    }

    public void setDataSubCategory(String dataSubCategory) {
        this.dataSubCategory = dataSubCategory;
    }

    @ApiModelProperty(notes = "A string identity to identify the source system type. It is used by consumer for filtering on the specific sources")
    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    @ApiModelProperty(notes = "The format of the file")
    public String getDataEncoding() {
        return dataEncoding;
    }

    public void setDataEncoding(String dataEncoding) {
        this.dataEncoding = dataEncoding;
    }

    @ApiModelProperty(notes = "An optional parameter that is used only if the encoding supports a schema lookup")
    public String getDataSpecRef() {
        return dataSpecRef;
    }

    public void setDataSpecRef(String dataSpecRef) {
        this.dataSpecRef = dataSpecRef;
    }

    @ApiModelProperty(notes = "SFTP or MINIO")
    public String getEndpointInterface() {
        return endpointInterface;
    }

    public void setEndpointInterface(String endpointInterface) {
        this.endpointInterface = endpointInterface;
    }
}