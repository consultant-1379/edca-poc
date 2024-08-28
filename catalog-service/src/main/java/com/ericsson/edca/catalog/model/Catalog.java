package com.ericsson.edca.catalog.model;

import javax.persistence.*;

@Entity
@IdClass(CatalogId.class)
public class Catalog {

    @Id
    private String dataType;
    @Id
    private String dataNotification;
    private String[] dataSubType;
    private String[] sourceType;
    @Enumerated(EnumType.STRING)
    private Encoding encoding;
    private String specRef;
    private String address;


    public Catalog() {
    }

    public Catalog(String dataType, String dataNotification, String[] dataSubType, String[] sourceType,
                   Encoding encoding, String specRef, String address) {
        super();
        this.dataType = dataType;
        this.dataNotification = dataNotification;
        this.dataSubType = dataSubType;
        this.sourceType = sourceType;
        this.encoding = encoding;
        this.specRef = specRef;
        this.address = address;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataNotification() {
        return dataNotification;
    }

    public void setDataNotification(String dataNotification) {
        this.dataNotification = dataNotification;
    }

    public String[] getDataSubType() {
        return dataSubType;
    }

    public void setDataSubType(String[] dataSubType) {
        this.dataSubType = dataSubType;
    }

    public String[] getSourceType() {
        return sourceType;
    }

    public void setSourceType(String[] sourceType) {
        this.sourceType = sourceType;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    public String getSpecRef() {
        return specRef;
    }

    public void setSpecRef(String specRef) {
        this.specRef = specRef;
    }


}
