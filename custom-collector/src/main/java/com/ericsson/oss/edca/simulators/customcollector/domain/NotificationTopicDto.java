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

import java.util.Collections;
import java.util.Set;

public class NotificationTopicDto {
    private String name;

    private int id;

    private String specificationReference;

    private NotificationEncoding encoding;

    private Set<Integer> fileFormatIds = Collections.emptySet();

    private MessageBusDto messageBus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecificationReference() {
        return specificationReference;
    }

    public void setSpecificationReference(String specificationReference) {
        this.specificationReference = specificationReference;
    }

    public NotificationEncoding getEncoding() {
        return encoding;
    }

    public void setEncoding(NotificationEncoding encoding) {
        this.encoding = encoding;
    }

    public Set<Integer> getFileFormatIds() {
        return fileFormatIds;
    }

    public void setFileFormatIds(Set<Integer> fileFormatIds) {
        this.fileFormatIds = fileFormatIds;
    }

    public MessageBusDto getMessageBus() {
        return messageBus;
    }

    public void setMessageBusDto(MessageBusDto messageBus) {
        this.messageBus = messageBus;
    }

    @Override
    public String toString() {
        return "NotificationTopicDto [name=" + name + ", id=" + id + ", specificationReference=" + specificationReference + ", encoding=" + encoding + ", fileFormatIds=" + fileFormatIds
                + ", messageBusDto=" + messageBus + "]";
    }

}
