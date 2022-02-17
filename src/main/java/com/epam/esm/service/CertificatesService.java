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
        long certificateId = certificateDao.createCertificate(certificate, dto.getDescription());
        certificate.setId(certificateId);

        return DtoConverter.toCertificateDto(certificate);
    }

    public CertificateDto getCertificate(long id) {
        return certificateDao.getCertificateById(id);
    }

    public List<CertificateDto> getAllCertificates() {
        return certificateDao.getAllCertificates();
    }

    public boolean updateCertificate(CertificateDto dto) {
        //certificateDao.update()
        System.out.println(dto);
        return true;
    }

    public boolean deleteCertificate(long id) {
        return certificateDao.delete(id);
    }

}
