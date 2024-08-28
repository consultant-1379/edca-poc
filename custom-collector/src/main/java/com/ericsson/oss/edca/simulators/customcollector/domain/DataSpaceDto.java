package com.ericsson.oss.edca.simulators.customcollector.domain;

import java.util.*;

public class DataSpaceDto {

    private String name;

    private int id;

    Set<Integer> DataProviderTypeIds = new HashSet<>();

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

    /**
     * @return the dataProviderTypeIds
     */
    public Set<Integer> getDataProviderTypeIds() {
        return Collections.unmodifiableSet(DataProviderTypeIds);
    }

    /**
     * @param dataProviderTypeIds
     *            the dataProviderTypeIds to set
     */
    public void setDataProviderTypeIds(final Set<Integer> dataProviderTypeIds) {
        DataProviderTypeIds = new HashSet<>(dataProviderTypeIds);
    }

    @Override
    public String toString() {
        return "DataSpaceDto [name=" + name + ", id=" + id + ", DataProviderTypeIds=" + DataProviderTypeIds + "]";
    }

}