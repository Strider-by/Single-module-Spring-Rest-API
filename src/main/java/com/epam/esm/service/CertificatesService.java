package com.epam.esm.service;

import com.epam.esm.controller.api.dto.CertificateCreateDto;
import com.epam.esm.entity.Certificate;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

public interface CertificatesService {

    Certificate createCertificate(CertificateCreateDto dto);

    Certificate getCertificate(long id);

    List<Certificate> getAllCertificates();

    Certificate updateCertificate(long id, MultiValueMap<String, String> params);

    boolean deleteCertificate(long id);

    List<Certificate> searchCertificates(Map<String, String> parameters);

}
