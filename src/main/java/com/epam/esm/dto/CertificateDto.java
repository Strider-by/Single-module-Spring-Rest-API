package com.epam.esm.dto;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

public class CertificateDto {

    private long id;
    private String name;
    private List<String> description;
    private int price;
    private int duration;
    private String created;
    private String lastUpdate;

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

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    @Override
    public String toString() {
        return "CertificateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description=" + description +
                ", price=" + price +
                ", duration=" + duration +
                ", created='" + created + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }

}
