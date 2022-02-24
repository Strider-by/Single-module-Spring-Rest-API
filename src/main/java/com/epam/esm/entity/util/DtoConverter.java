package com.epam.esm.entity.util;

import com.epam.esm.controller.api.dto.CertificateDownstreamDto;
import com.epam.esm.controller.api.dto.CertificateUpstreamDto;
import com.epam.esm.controller.util.DateConverter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.stream.Collectors;

public class DtoConverter {

//    /**
//     * {@link CertificateDownstreamDto} does not contain dates so the {@link Certificate} object that is to be returned
//     * by this method has empty date fields.
//     */
//    public static Certificate toCertificate(CertificateDownstreamDto dto) {
//
//        if (dto == null) {
//            return null;
//        }
//
//        Certificate certificate = new Certificate();
//        certificate.setName(dto.getName());
//        certificate.setDescription(
//                dto.getDescription().stream()
//                        .map(DtoConverter::toTag)
//                        .collect(Collectors.toList()));
//        certificate.setPrice(dto.getPrice());
//        certificate.setDuration(dto.getDuration());
//
//        return certificate;
//    }

//    public static CertificateUpstreamDto toCertificateUpstreamDto(Certificate certificate) {
//
//        if (certificate == null) {
//            return null;
//        }
//
//        CertificateUpstreamDto dto = new CertificateUpstreamDto();
//        dto.setId(certificate.getId());
//        dto.setName(certificate.getName());
//        dto.setDescription(
//                certificate.getDescription().stream()
//                .map(Tag::getName)
//                .collect(Collectors.toList()));
//        dto.setPrice(certificate.getPrice());
//        dto.setDuration(certificate.getDuration());
//        dto.setCreated(DateConverter.toISO8601DateString(certificate.getCreateDate()));
//        dto.setLastUpdate(DateConverter.toISO8601DateString(certificate.getLastUpdateDate()));
//
//        return dto;
//    }

//    public static Tag toTag(String string) {
//        Tag tag = new Tag();
//        tag.setName(string);
//        return tag;
//    }

//    public static to

}
