package com.epam.esm.service;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CertificatesService {

    @Autowired
    private CertificateDao certificateDao;

    public CertificatesService(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    public CertificateDto createCertificate(CertificateDto dto) {
        Certificate certificate = DtoConverter.toCertificate(dto);
        certificate.affixCreateTimestamp();
        long certificateId = certificateDao.create(certificate);
        certificate.setId(certificateId);

        return DtoConverter.toCertificateDto(certificate);
    }

    public CertificateDto getCertificate(long id) {
        Certificate certificate = certificateDao.getById(id);
        return DtoConverter.toCertificateDto(certificate);
    }

    public List<CertificateDto> getAllCertificates() {
        return certificateDao.getAll().stream()
                .map(DtoConverter::toCertificateDto)
                .collect(Collectors.toList());
    }

    public boolean deleteCertificate(long id) {
        return certificateDao.delete(id);
    }

}
