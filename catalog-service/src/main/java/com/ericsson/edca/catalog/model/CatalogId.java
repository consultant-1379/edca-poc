package com.ericsson.edca.catalog.model;

import java.io.Serializable;
import java.util.Objects;


public class CatalogId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6213004770221421209L;

    private String dataType;
    private String dataNotification;

    public CatalogId() {
    }

    public CatalogId(String dataType, String dataNotification) {
        this.dataType = dataType;
        this.dataNotification = dataNotification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogId catalogId = (CatalogId) o;
        return dataType.equals(catalogId.dataType) &&
                dataNotification.equals(catalogId.dataNotification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataType, dataNotification);
    }

}
