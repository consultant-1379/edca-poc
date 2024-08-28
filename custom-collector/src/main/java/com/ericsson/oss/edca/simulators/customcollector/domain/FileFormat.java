/*******************************************************************************

* COPYRIGHT Ericsson 2020
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
* 
******************************************************************************/
package com.ericsson.oss.edca.simulators.customcollector.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * File type, we assume all the files notifications of the same type and format will go to the same topic (regardless of time period)
 */
public class FileFormat implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Set<Integer> reportOutputPeriodList;
    private DataEncoding dataEncoding;
    private String specificationReference;
    private int dataProviderTypeId;
    private String dataCollectorId;
    private int notificationTopicId;
    private int bulkDataRepositoryId;

    public Set<Integer> getReportOutputPeriodList() {
        return reportOutputPeriodList;
    }

    public void setReportOutputPeriodList(Set<Integer> reportOutputPeriodList) {
        this.reportOutputPeriodList = reportOutputPeriodList;
    }

    public DataEncoding getDataEncoding() {
        return dataEncoding;
    }

    public void setDataEncoding(DataEncoding dataEncoding) {
        this.dataEncoding = dataEncoding;
    }

    public String getSpecificationReference() {
        return specificationReference;
    }

    public void setSpecificationReference(String specificationReference) {
        this.specificationReference = specificationReference;
    }

    public int getDataProviderTypeId() {
        return dataProviderTypeId;
    }

    public void setDataProviderTypeId(int dataProviderTypeId) {
        this.dataProviderTypeId = dataProviderTypeId;
    }

    public String getDataCollectorId() {
        return dataCollectorId;
    }

    public void setDataCollectorId(String dataCollectorId) {
        this.dataCollectorId = dataCollectorId;
    }

    public int getNotificationTopicId() {
        return notificationTopicId;
    }

    public void setNotificationTopicId(int notificationTopicId) {
        this.notificationTopicId = notificationTopicId;
    }

    public int getBulkDataRepositoryId() {
        return bulkDataRepositoryId;
    }

    public void setBulkDataRepositoryId(int bulkDataRepositoryId) {
        this.bulkDataRepositoryId = bulkDataRepositoryId;
    }

    @Override
    public String toString() {
        return "FileFormat [reportOutputPeriodList=" + reportOutputPeriodList + ", dataEncoding=" + dataEncoding + ", specificationReference=" + specificationReference + ", dataProviderTypeId="
                + dataProviderTypeId + ", dataCollectorId=" + dataCollectorId + ", notificationTopicId=" + notificationTopicId + ", bulkDataRepositoryId=" + bulkDataRepositoryId + "]";
    }

}
