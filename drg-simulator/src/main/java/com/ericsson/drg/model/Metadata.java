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
package com.ericsson.drg.model;

import java.util.Collections;
import java.util.Set;

/**
 * Metadata Details
 * @author zkanris
 *
 */
public class Metadata {

    private String dataCategory;
    private String topicName;
    private Set<String> dataSubCategory = Collections.emptySet();
    private Set<String> sourceType = Collections.emptySet();
    private Encoding topicEncoding;
    private String topicSpecRef;
    private Set<String> accessEndpoints = Collections.emptySet();

    public Metadata() {

    }

    public Metadata(final String dataCategory, final String topicName, final Set<String> dataSubCategory,
            final Set<String> sourceType, final Encoding topicEncoding, final String topicSpecRef,
            final Set<String> accessEndpoints) {
        super();
        this.dataCategory = dataCategory;
        this.topicName = topicName;
        this.dataSubCategory = Collections.unmodifiableSet(dataSubCategory);
        this.sourceType = Collections.unmodifiableSet(sourceType);
        this.topicEncoding = topicEncoding;
        this.topicSpecRef = topicSpecRef;
        this.accessEndpoints = Collections.unmodifiableSet(accessEndpoints);
    }

    public String getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(final String dataCategory) {
        this.dataCategory = dataCategory;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(final String topicName) {
        this.topicName = topicName;
    }

    public Set<String> getDataSubCategory() {
        return Collections.unmodifiableSet(dataSubCategory);
    }

    public void setDataSubCategory(final Set<String> dataSubCategory) {
        this.dataSubCategory = Collections.unmodifiableSet(dataSubCategory);
    }

    public Set<String> getSourceType() {
        return Collections.unmodifiableSet(sourceType);
    }

    public void setSourceType(final Set<String> sourceType) {
        this.sourceType = Collections.unmodifiableSet(sourceType);
    }

    public Set<String> getAccessEndpoints() {
        return Collections.unmodifiableSet(accessEndpoints);
    }

    public void setAccessEndpoints(final Set<String> accessEndpoints) {
        this.accessEndpoints = Collections.unmodifiableSet(accessEndpoints);
    }

    public Encoding getTopicEncoding() {
        return topicEncoding;
    }

    public void setTopicEncoding(final Encoding topicEncoding) {
        this.topicEncoding = topicEncoding;
    }

    public String getTopicSpecRef() {
        return topicSpecRef;
    }

    public void setTopicSpecRef(final String topicSpecRef) {
        this.topicSpecRef = topicSpecRef;
    }

    @Override
    public String toString() {
        return "Metadata [dataCategory=" + dataCategory + ", topicName=" + topicName + ", dataSubCategory="
                + dataSubCategory + ", sourceType=" + sourceType + ", topicEncoding=" + topicEncoding
                + ", topicSpecRef=" + topicSpecRef + ", accessEndpoints=" + accessEndpoints + "]";
    }

}
