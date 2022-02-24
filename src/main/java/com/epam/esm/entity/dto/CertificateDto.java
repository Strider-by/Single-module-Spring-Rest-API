//package com.epam.esm.entity.dto;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class CertificateDto {
//
//    private Long id;
//    private String name;
//    private List<String> description;
//    private int price;
//    private int duration;
//    private String created;
//    private String lastUpdate;
//
//    public CertificateDto() {
//    }
//
//    public CertificateDto(CertificateDto dto) {
//        this.id = dto.id;
//        this.name = dto.name;
//        this.description = dto.description == null ? null : (ArrayList<String>) dto.description.clone();
//        this.price = dto.price;
//        this.duration = dto.duration;
//        this.created = dto.created;
//        this.lastUpdate = dto.lastUpdate;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public List<String> getDescription() {
//        return description;
//    }
//
//    public void setDescription(List<String> description) {
//        this.description = description;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public int getDuration() {
//        return duration;
//    }
//
//    public void setDuration(int duration) {
//        this.duration = duration;
//    }
//
//    public String getCreated() {
//        return created;
//    }
//
//    public void setCreated(String created) {
//        this.created = created;
//    }
//
//    public String getLastUpdate() {
//        return lastUpdate;
//    }
//
//    public void setLastUpdate(String lastUpdate) {
//        this.lastUpdate = lastUpdate;
//    }
//
//
//    @Override
//    public String toString() {
//        return "CertificateDto{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", description=" + description +
//                ", price=" + price +
//                ", duration=" + duration +
//                ", created='" + created + '\'' +
//                ", lastUpdate='" + lastUpdate + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CertificateDto that = (CertificateDto) o;
//        return price == that.price && duration == that.duration && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(created, that.created) && Objects.equals(lastUpdate, that.lastUpdate);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, description, price, duration, created, lastUpdate);
//    }
//
//}
