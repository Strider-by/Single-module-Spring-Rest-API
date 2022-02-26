package com.epam.esm.service;

import com.epam.esm.controller.api.dto.CertificateDownstreamDto;
import com.epam.esm.controller.api.dto.CertificateUpstreamDto;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.dto.CertificateUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class CertificatesServiceImpl {

    @Autowired
    private CertificateDao certificateDao;

    public CertificatesServiceImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    public Certificate createCertificate(CertificateDownstreamDto dto) {
        Date createdAt = new Date();
        return certificateDao.createCertificate(dto, createdAt);
    }

    public Certificate getCertificate(long id) {
        return certificateDao.getCertificateById(id);
    }

    public List<Certificate> getAllCertificates() {
        return certificateDao.getAllCertificates();
    }

    public Certificate updateCertificate(long id, MultiValueMap<String, String> params) {
        CertificateUpdateDto dto = new CertificateUpdateDto();

        dto.setId(id);
        if (params.containsKey("name")) dto.setName(params.get("name").get(0));
        if (params.containsKey("description")) dto.setDescription(params.get("description"));
        if (params.containsKey("price")) dto.setPrice(Integer.parseInt(params.get("price").get(0)));
        if (params.containsKey("duration")) dto.setPrice(Integer.parseInt(params.get("duration").get(0)));
        dto.affixUpdateTimestamp();

        return certificateDao.update(dto);
    }

    public boolean deleteCertificate(long id) {
        return certificateDao.delete(id);
    }

//    public List<CertificateDto> getCertificatesByTagName(String tagName) {
//        return certificateDao.getCertificatesByTagName(tagName);
//    }

    public List<Certificate> searchCertificates(Map<String, String> parameters) {
        return certificateDao.searchCertificates(parameters);
    }

}
