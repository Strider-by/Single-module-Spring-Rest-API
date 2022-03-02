package com.epam.esm.dao;

import com.epam.esm.controller.api.dto.CertificateCreateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.dto.CertificateUpdateDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CertificateDao {

    Certificate createCertificate(CertificateCreateDto dto, Date createdAt);

    Certificate getCertificateById(long id);

    List<Certificate> getAllCertificates();

    Certificate update(CertificateUpdateDto dto);

    boolean delete(long id);

    List<Certificate> searchCertificates(Map<String, String> parameters);

}
