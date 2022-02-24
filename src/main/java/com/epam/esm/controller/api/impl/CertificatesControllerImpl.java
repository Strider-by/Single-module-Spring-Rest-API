package com.epam.esm.controller.api.impl;

import com.epam.esm.controller.api.CertificatesController;
import com.epam.esm.controller.api.dto.CertificateDownstreamDto;
import com.epam.esm.controller.api.dto.CertificateUpstreamDto;
import com.epam.esm.controller.api.exception.CertificateNotFoundException;
import com.epam.esm.controller.util.DtoConverter;
import com.epam.esm.controller.util.Message;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificatesControllerImpl implements CertificatesController {

    static {
        System.out.println("CertificatesController.static initializer");
    }

    @Autowired
    private final CertificatesService certificatesService;

    public CertificatesControllerImpl(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Certificate> getAllCertificates() {
        return certificatesService.getAllCertificates();
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Certificate getCertificate(@PathVariable long id) {
        Certificate certificate = certificatesService.getCertificate(id);
        if (certificate == null) {
            throw new CertificateNotFoundException(id);
        }
        return certificate;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    CertificateUpstreamDto createCertificate(CertificateDownstreamDto dto) {
        return DtoConverter.toCertificateUpstreamDto(certificatesService.createCertificate(dto));
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody
    Message deleteCertificate(@PathVariable long id) {
        boolean deleted = certificatesService.deleteCertificate(id);
        if (!deleted) {
            throw new CertificateNotFoundException(id);
        }

        return new Message(HttpStatus.OK, String.format("Certificate %d has been deleted", id));
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Certificate updateCertificate(
            @PathVariable("id") long id,
            @RequestBody MultiValueMap<String, String> params) {

        Certificate certificate = certificatesService.updateCertificate(id, params);
        if (certificate == null) {
            throw new CertificateNotFoundException(id);
        }

        return certificate;
    }


//    @RequestMapping(value="/{tagName}", method=RequestMethod.GET, produces="application/json")
//    public  @ResponseBody List<CertificateDto> getCertificateByTagName(@PathVariable String tagName) {
//        List<CertificateDto> searchResult = certificatesService.getCertificatesByTagName(tagName);
//        return searchResult;
//    }


    @ExceptionHandler(CertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Message certificateNotFound(CertificateNotFoundException ex) {
        long id = ex.getCertificateId();
        return new Message(HttpStatus.NOT_FOUND, String.format("Certificate %d can not be found", id));
    }

}
