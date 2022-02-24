package com.epam.esm.dao.impl;

import com.epam.esm.entity.Certificate;
//import com.epam.esm.entity.dto.CertificateDto;
import com.epam.esm.controller.util.DateConverter;

import java.util.ArrayList;
import java.util.Date;

public class CertificateDataRow {

    private Long id;
    private String name;
    private String tagName;
    private int price;
    private int duration;
    private Date created;
    private Date lastUpdate;

    public CertificateDataRow(Long id, String name, String tagName, int price, int duration, Date created, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.tagName = tagName;
        this.price = price;
        this.duration = duration;
        this.created = created;
        this.lastUpdate = lastUpdate;
    }

    public CertificateDataRow() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Certificate toCertificate() {
        Certificate certificate = new Certificate();
        certificate.setId(this.id);
        certificate.setName(this.name);
        certificate.setDuration(this.duration);
        certificate.setPrice(this.price);
        certificate.setCreateDate(this.created);
        certificate.setLastUpdateDate(this.lastUpdate);
        certificate.setDescription(new ArrayList<>());
        return certificate;
    }

}
