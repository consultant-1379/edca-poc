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
import java.net.URL;

public class DataCollector implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String collectorId;
    private URL controlEndpoint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
    }

    public URL getControlEndpoint() {
        return controlEndpoint;
    }

    public void setControlEndpoint(URL controlEndpoint) {
        this.controlEndpoint = controlEndpoint;
    }

    @Override
    public String toString() {
        return "DataCollector [collectorId=" + collectorId + ", controlEndpoint=" + controlEndpoint + "]";
    }

}
