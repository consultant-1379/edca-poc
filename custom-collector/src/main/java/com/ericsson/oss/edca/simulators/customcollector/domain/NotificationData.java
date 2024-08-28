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

import java.util.Set;

public class NotificationData {

    private String topicName;

    private String dataCategory;

    private Set<String> dataSubCategory;

    private Set<String> sourceType;

    private NotificationEncoding topicEncoding;

    private String topicSpecRef;

    private Set<String> accessEndpoints;

    public NotificationData(String topicName, String dataCategory, Set<String> dataSubCategory, Set<String> sourceType, NotificationEncoding topicEncoding, String topicSpecRef,
            Set<String> accessEndpoints) {
        this.topicName = topicName;
        this.dataCategory = dataCategory;
        this.dataSubCategory = dataSubCategory;
        this.sourceType = sourceType;
        this.topicEncoding = topicEncoding;
        this.topicSpecRef = topicSpecRef;
        this.accessEndpoints = accessEndpoints;
    }

    public NotificationData() {
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(String dataCategory) {
        this.dataCategory = dataCategory;
    }

    public Set<String> getDataSubCategory() {
        return dataSubCategory;
    }

    public void setDataSubCategory(Set<String> dataSubCategory) {
        this.dataSubCategory = dataSubCategory;
    }

    public Set<String> getSourceType() {
        return sourceType;
    }

    public void setSourceType(Set<String> sourceType) {
        this.sourceType = sourceType;
    }

    public NotificationEncoding getTopicEncoding() {
        return topicEncoding;
    }

    public void setTopicEncoding(NotificationEncoding topicEncoding) {
        this.topicEncoding = topicEncoding;
    }

    public String getTopicSpecRef() {
        return topicSpecRef;
    }

    public void setTopicSpecRef(String topicSpecRef) {
        this.topicSpecRef = topicSpecRef;
    }

    public Set<String> getAccessEndpoints() {
        return accessEndpoints;
    }

    public void setAccessEndpoints(Set<String> accessEndpoints) {
        this.accessEndpoints = accessEndpoints;
    }

    @Override
    public String toString() {
        return "Metadata [accessEndpoints=" + accessEndpoints + ", dataCategory=" + dataCategory + ", dataSubCategory=" + dataSubCategory + ", sourceType=" + sourceType + ", topicEncoding="
                + topicEncoding + ", topicName=" + topicName + ", topicSpecRef=" + topicSpecRef + "]";
    }
}