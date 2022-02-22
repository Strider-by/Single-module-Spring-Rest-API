package com.epam.esm.dao;

import com.epam.esm.entity.dto.CertificateDto;
import com.epam.esm.entity.dto.CertificateUpdateDto;
import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Map;

public interface CertificateDao {

    CertificateDto createCertificate(Certificate certificate, List<String> description);

    CertificateDto getCertificateById(long id);

    List<CertificateDto> getAllCertificates();

    CertificateDto update(CertificateUpdateDto dto);

    boolean delete(long id);

//    List<CertificateDto> getCertificatesByTagName(String tagName);

    List<CertificateDto> searchCertificates(Map<String, String> parameters);

}
