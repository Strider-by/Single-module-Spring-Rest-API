package com.epam.esm.controller.api.dto;

public class TagUpstreamDto {

    private String name;

    public TagUpstreamDto(String name) {
        this.name = name;
    }

    public TagUpstreamDto() {
    }

    public String getName() {
        return name;
    }

}
