package com.epam.esm.dao;

import com.epam.esm.controller.api.dto.CertificateDownstreamDto;
//import com.epam.esm.entity.dto.CertificateDto;
import com.epam.esm.entity.dto.CertificateUpdateDto;
import com.epam.esm.entity.Certificate;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CertificateDao {

    Certificate createCertificate(CertificateDownstreamDto dto, Date createdAt);

    Certificate getCertificateById(long id);

    List<Certificate> getAllCertificates();

    Certificate update(CertificateUpdateDto dto);

    boolean delete(long id);

//    List<CertificateDto> getCertificatesByTagName(String tagName);

    List<Certificate> searchCertificates(Map<String, String> parameters);

}
