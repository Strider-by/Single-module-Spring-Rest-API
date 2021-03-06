package com.epam.esm.controller.api;

import com.epam.esm.controller.api.dto.CertificateCreateDto;
import com.epam.esm.controller.api.dto.CertificateUpstreamDto;
import com.epam.esm.controller.util.Message;
import com.epam.esm.entity.Certificate;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/certificates")
public interface CertificatesController {

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    List<Certificate> getAllCertificates();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    Certificate getCertificate(@PathVariable long id);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    CertificateUpstreamDto createCertificate(CertificateCreateDto dto);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    Message deleteCertificate(@PathVariable long id);

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = "application/json")
    @ResponseBody
    Certificate updateCertificate(
            @PathVariable("id") long id,
            @RequestBody MultiValueMap<String, String> params);

}
