package com.epam.esm.controller.api.impl;

import com.epam.esm.controller.api.CertificatesController;
import com.epam.esm.controller.api.dto.CertificateCreateDto;
import com.epam.esm.controller.api.dto.CertificateUpstreamDto;
import com.epam.esm.controller.api.exception.CertificateNotFoundException;
import com.epam.esm.controller.util.DtoConverter;
import com.epam.esm.controller.util.Message;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CertificatesControllerImpl implements CertificatesController {

    @Autowired
    private CertificatesService certificatesService;

    public CertificatesControllerImpl(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    public CertificatesControllerImpl() {
    }

    public void setCertificatesService(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @Override
    public List<Certificate> getAllCertificates() {
        return certificatesService.getAllCertificates();
    }

    @Override
    public Certificate getCertificate(long id) {
        Certificate certificate = certificatesService.getCertificate(id);
        if (certificate == null) {
            throw new CertificateNotFoundException(id);
        }
        return certificate;
    }

    @Override
    public CertificateUpstreamDto createCertificate(CertificateCreateDto dto) {
        return DtoConverter.toCertificateUpstreamDto(certificatesService.createCertificate(dto));
    }

    @Override
    public Message deleteCertificate(long id) {
        boolean deleted = certificatesService.deleteCertificate(id);
        if (!deleted) {
            throw new CertificateNotFoundException(id);
        }

        return new Message(HttpStatus.OK, String.format("Certificate %d has been deleted", id));
    }

    @Override
    public Certificate updateCertificate(long id, MultiValueMap<String, String> params) {

        Certificate certificate = certificatesService.updateCertificate(id, params);
        if (certificate == null) {
            throw new CertificateNotFoundException(id);
        }

        return certificate;
    }

    @ExceptionHandler(CertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(produces = "application/json")
    private Message certificateNotFound(CertificateNotFoundException ex) {
        long id = ex.getCertificateId();
        return new Message(HttpStatus.NOT_FOUND, String.format("Certificate %d can not be found", id));
    }

}
