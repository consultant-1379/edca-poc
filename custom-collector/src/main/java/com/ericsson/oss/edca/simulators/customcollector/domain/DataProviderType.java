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

/**
 * Type of data provider. That loosely maps to a type of node and data type e.g. PM stats generating function of a node. Data collectors may not have a clear ide of what node they are dealing with if
 * the same model/subsystem generates data - they are dealing with the same interface!
 */

public class DataProviderType implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int dataSpaceId;
    private String providerVersion;
    private DataCategory dataCategory;
    private String providerTypeId;

    public int getDataSpaceId() {
        return dataSpaceId;
    }

    public void setDataSpaceId(int dataSpaceId) {
        this.dataSpaceId = dataSpaceId;
    }

    public String getProviderVersion() {
        return providerVersion;
    }

    public void setProviderVersion(String providerVersion) {
        this.providerVersion = providerVersion;
    }

    public DataCategory getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(DataCategory dataCategory) {
        this.dataCategory = dataCategory;
    }

    public String getProviderTypeId() {
        return providerTypeId;
    }

    public void setProviderTypeId(String providerTypeId) {
        this.providerTypeId = providerTypeId;
    }

    @Override
    public String toString() {
        return "DataProviderType [dataSpaceId=" + dataSpaceId + ", providerVersion=" + providerVersion + ", dataCategory=" + dataCategory + ", providerTypeId=" + providerTypeId + "]";
    }

}
