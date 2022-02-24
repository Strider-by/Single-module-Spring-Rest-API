package com.epam.esm.controller.util;

import com.epam.esm.controller.api.dto.CertificateUpstreamDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.stream.Collectors;

public class DtoConverter {

    public static CertificateUpstreamDto toCertificateUpstreamDto(Certificate certificate) {

        if (certificate == null) {
            return null;
        }

        CertificateUpstreamDto dto = new CertificateUpstreamDto();
        dto.setId(certificate.getId());
        dto.setName(certificate.getName());
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

}
