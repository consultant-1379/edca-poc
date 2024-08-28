/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2020
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.edca.simulators.customcollector.domain;

import java.util.*;

public class DataProviderTypeDto {

    private int id;
    private DataSpaceDto dataSpace;
    private String providerVersion;
    private DataCategory dataCategory;
    private String providerTypeId;
    private Set<Integer> fileFormatIds = Collections.emptySet();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DataSpaceDto getDataSpace() {
        return dataSpace;
    }

    public void setDataSpace(DataSpaceDto dataSpace) {
        this.dataSpace = dataSpace;
    }

    public String getProviderVersion() {
        return providerVersion;
    }

    public void setProviderVersion(final String providerVersion) {
        this.providerVersion = providerVersion;
    }

    public DataCategory getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(final DataCategory dataCategory) {
        this.dataCategory = dataCategory;
    }

    public String getProviderTypeId() {
        return providerTypeId;
    }

    public void setProviderTypeId(final String providerTypeId) {
        this.providerTypeId = providerTypeId;
    }

    public Set<Integer> getFileFormatIds() {
        return Collections.unmodifiableSet(fileFormatIds);
    }

    public void setFileFormatIds(final Set<Integer> fileFormatIds) {
        this.fileFormatIds = new HashSet<>(fileFormatIds);
    }

    @Override
    public String toString() {
        return "DataProviderTypeDto [id=" + id + ", dataSpaceDto=" + dataSpace + ", providerVersion=" + providerVersion + ", dataCategory=" + dataCategory + ", providerTypeId=" + providerTypeId
                + ", fileFormatIds=" + fileFormatIds + "]";
    }

}
