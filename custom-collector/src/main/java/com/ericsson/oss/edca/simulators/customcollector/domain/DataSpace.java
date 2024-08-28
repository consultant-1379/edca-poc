package com.ericsson.oss.edca.simulators.customcollector.domain;

import java.io.Serializable;

public class DataSpace implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
