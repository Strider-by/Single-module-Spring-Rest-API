package com.epam.esm.entity.util;

import com.epam.esm.entity.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.stream.Collectors;

public class DtoConverter {

    public static Certificate toCertificate(CertificateDto dto) {

        if (dto == null) {
            return null;
        }

        Certificate certificate = new Certificate();
        certificate.setName(dto.getName());
        certificate.setDescription(
                dto.getDescription().stream()
                        .map(DtoConverter::toTag)
                        .collect(Collectors.toList()));
        certificate.setPrice(dto.getPrice());
        certificate.setDuration(dto.getDuration());

        certificate.setCreateDate(DateConverter.milliSecondsToDate(dto.getCreated()));
        certificate.setLastUpdateDate(DateConverter.milliSecondsToDate(dto.getLastUpdate()));

        return certificate;
    }

    public static CertificateDto toCertificateDto(Certificate certificate) {

        if (certificate == null) {
            return null;
        }

        CertificateDto dto = new CertificateDto();
        dto.setId(certificate.getId());
        dto.setName(certificate.getName());
        // description
        dto.setDescription(
                certificate.getDescription().stream()
                .map(Tag::getName)
                .collect(Collectors.toList()));
        dto.setPrice(certificate.getPrice());
        dto.setDuration(certificate.getDuration());
        dto.setCreated(DateConverter.toISO8601DateString(certificate.getCreateDate()));
        dto.setLastUpdate(DateConverter.toISO8601DateString(certificate.getLastUpdateDate()));

        return dto;
    }

    public static Tag toTag(String string) {
        Tag tag = new Tag();
        tag.setName(string);
        return tag;
    }

}
