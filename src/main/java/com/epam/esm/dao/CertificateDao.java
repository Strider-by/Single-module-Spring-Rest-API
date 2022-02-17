package com.epam.esm.dao;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Map;

public interface CertificateDao {

    long createCertificate(Certificate certificate, List<String> description);

    CertificateDto getCertificateById(long id);

    List<CertificateDto> getAllCertificates();

    Certificate update(Map<String, Object> nameValuePairs);

    boolean delete(long id);

}
