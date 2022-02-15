package com.epam.esm.controller.api;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificatesController {

    @Autowired
    private CertificatesService certificatesService;

    static {
        System.out.println("CertificatesController.static initializer");
    }

    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public  @ResponseBody
    List<CertificateDto> getAllCertificates() {
        return certificatesService.getAllCertificates();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET, produces="application/json")
    public  @ResponseBody CertificateDto getCertificate(@PathVariable long id) {
        return certificatesService.getCertificate(id);
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json")
    public  @ResponseBody CertificateDto createCertificate(CertificateDto certificateDto) {
        return certificatesService.createCertificate(certificateDto);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE, produces="application/json")
    public  @ResponseBody boolean deleteCertificate(@PathVariable long id) {
        return certificatesService.deleteCertificate(id);
    }




}
