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

public class NotificationTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    private int messageBusId;
    private String name;
    private String encoding;
    private String specificationReference;

    public int getMessageBusId() {
        return messageBusId;
    }

    public void setMessageBusId(int messageBusId) {
        this.messageBusId = messageBusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSpecificationReference() {
        return specificationReference;
    }

    public void setSpecificationReference(String specificationReference) {
        this.specificationReference = specificationReference;
    }

    @Override
    public String toString() {
        return "NotificationTopic [messageBusId=" + messageBusId + ", name=" + name + ", encoding=" + encoding + ", specificationReference=" + specificationReference + "]";
    }

}
