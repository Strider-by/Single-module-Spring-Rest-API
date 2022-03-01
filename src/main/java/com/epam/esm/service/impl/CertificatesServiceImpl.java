package com.epam.esm.service.impl;

import com.epam.esm.controller.api.dto.CertificateCreateDto;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.dto.CertificateUpdateDto;
import com.epam.esm.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class CertificatesServiceImpl implements CertificatesService {

    @Autowired
    private CertificateDao certificateDao;

    public CertificatesServiceImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public Certificate createCertificate(CertificateCreateDto dto) {
        System.out.println("CertificatesServiceImpl.createCertificate");
        Date createdAt = new Date();
        return certificateDao.createCertificate(dto, createdAt);
    }

    @Override
    public Certificate getCertificate(long id) {
        return certificateDao.getCertificateById(id);
    }

    @Override
    public List<Certificate> getAllCertificates() {
        System.err.print("CertificatesServiceImpl.getAllCertificates");
        return certificateDao.getAllCertificates();
    }

    @Override
    public Certificate updateCertificate(long id, MultiValueMap<String, String> params) {
        CertificateUpdateDto dto = new CertificateUpdateDto();

        dto.setId(id);
        if (params.containsKey("name")) dto.setName(params.get("name").get(0));
        if (params.containsKey("description")) dto.setDescription(params.get("description"));
        if (params.containsKey("price")) dto.setPrice(Integer.parseInt(params.get("price").get(0)));
        if (params.containsKey("duration")) dto.setDuration(Integer.parseInt(params.get("duration").get(0)));
        dto.affixUpdateTimestamp();

        return certificateDao.update(dto);
    }

    @Override
    public boolean deleteCertificate(long id) {
        return certificateDao.delete(id);
    }

    @Override
    public List<Certificate> searchCertificates(Map<String, String> parameters) {
        return certificateDao.searchCertificates(parameters);
    }

}
