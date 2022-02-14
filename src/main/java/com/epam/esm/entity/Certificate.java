package com.epam.esm.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Certificate {

    private long id;
    private String name;
    private List<Tag> description;
    private int price;
    private int duration;
    private Date createDate;
    private Date lastUpdateDate;

    public Certificate() {
        this.description = new ArrayList<>();
    }

    public Certificate(long id, String name, List<Tag> description, int price, int duration, Date createDate, Date lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getDescription() {
        return description;
    }

    public void setDescription(List<Tag> description) {
        this.description = description;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void addToDescription(Tag tag) {
        this.description.add(tag);
    }

    public void affixCreateTimestamp() {
        this.createDate = new Date();
        this.lastUpdateDate = createDate;
    }

    public void affixUpdateTimestamp() {
        this.lastUpdateDate = new Date();
    }

}
