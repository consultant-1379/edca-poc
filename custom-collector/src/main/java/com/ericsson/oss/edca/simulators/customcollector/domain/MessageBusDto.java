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

import java.util.*;

public class MessageBusDto {
    private String name;

    private int id;

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

    private String clusterName;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    private String nameSpace;

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    private Set<String> accessEndpoints = Collections.emptySet();

    public Set<String> getAccessEndpoints() {
        return Collections.unmodifiableSet(accessEndpoints);
    }

    public void setAccessEndpoints(Set<String> accessEndpoints) {
        this.accessEndpoints = new HashSet<>(accessEndpoints);
    }

    private Set<Integer> notificationTopicIds = Collections.emptySet();

    public Set<Integer> getNotificationTopicIds() {
        return Collections.unmodifiableSet(notificationTopicIds);
    }

    public void setNotificationTopicIds(Set<Integer> notificationTopicIds) {
        this.notificationTopicIds = new HashSet<>(notificationTopicIds);
    }

    @Override
    public String toString() {
        return "MessageBusDto [name=" + name + ", id=" + id + ", clusterName=" + clusterName + ", nameSpace=" + nameSpace + ", accessEndpoints=" + accessEndpoints + ", notificationTopicIds="
                + notificationTopicIds + "]";
    }

}
