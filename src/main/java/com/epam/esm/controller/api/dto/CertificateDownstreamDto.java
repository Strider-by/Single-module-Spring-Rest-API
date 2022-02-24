package com.epam.esm.controller.api.dto;

import java.util.List;

public class CertificateDownstreamDto {

    private String name;
    private List<TagDownstreamDto> description;
    private int price;
    private int duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TagDownstreamDto> getDescription() {
        return description;
    }

    public void setDescription(List<TagDownstreamDto> description) {
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

}
