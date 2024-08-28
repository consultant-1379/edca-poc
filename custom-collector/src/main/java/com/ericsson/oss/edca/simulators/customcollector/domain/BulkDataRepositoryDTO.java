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
import java.util.HashSet;
import java.util.Set;

public class BulkDataRepositoryDTO {

    private String name;

    private int id;

    private String clusterName;

    private String nameSpace;

    private Set<String> accessEndpoints = Collections.emptySet();

    private Set<Integer> fileFormatIds = Collections.emptySet();

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

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public Set<String> getAccessEndpoints() {
        return Collections.unmodifiableSet(accessEndpoints);
    }

    public void setAccessEndpoints(Set<String> accessEndpoints) {
        this.accessEndpoints = new HashSet<>(accessEndpoints);
    }

    public Set<Integer> getFileFormatIds() {
        return Collections.unmodifiableSet(fileFormatIds);
    }

    public void setFileFormatIds(Set<Integer> fileFormatIds) {
        this.fileFormatIds = new HashSet<>(fileFormatIds);
    }

    @Override
    public String toString() {
        return "BulkDataRepositoryDTO [name=" + name + ", id=" + id + ", clusterName=" + clusterName + ", nameSpace=" + nameSpace + ", accessEndpoints=" + accessEndpoints + ", fileFormatIds="
                + fileFormatIds + "]";
    }

}
