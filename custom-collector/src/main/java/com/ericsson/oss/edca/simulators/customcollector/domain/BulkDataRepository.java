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

public class BulkDataRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String clusterName;
	private String nameSpace;
	private Set<String> accessEndpoints;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return accessEndpoints;
	}

	public void setAccessEndpoints(Set<String> accessEndpoints) {
		this.accessEndpoints = accessEndpoints;
	}

	@Override
	public String toString() {
		return "BulkDataRepository [name=" + name + ", clusterName=" + clusterName + ", nameSpace=" + nameSpace
				+ ", accessEndpoints=" + accessEndpoints + "]";
	}
}
