package com.epam.esm.controller.api;

import com.epam.esm.controller.api.exception.CertificateNotFoundException;
import com.epam.esm.controller.util.Message;
import com.epam.esm.entity.dto.CertificateDto;
import com.epam.esm.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
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
        CertificateDto certificateDto = certificatesService.getCertificate(id);
        if (certificateDto == null) {
            throw new CertificateNotFoundException(id);
        }
        return certificateDto;
    }

    @RequestMapping(method=RequestMethod.POST, produces="application/json")
    public  @ResponseBody CertificateDto createCertificate(CertificateDto certificateDto) {
        return certificatesService.createCertificate(certificateDto);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE, produces="application/json")
    public  @ResponseBody Message deleteCertificate(@PathVariable long id) {
        boolean deleted = certificatesService.deleteCertificate(id);
        if (!deleted) {
            throw new CertificateNotFoundException(id);
        }

        return new Message(HttpStatus.OK, String.format("Certificate %d has been deleted", id));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PATCH, produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public  @ResponseBody CertificateDto updateCertificate(
            @PathVariable("id") long id,
            @RequestBody MultiValueMap<String, String> params) {

        CertificateDto certificateDto = certificatesService.updateCertificate(id, params);
        if (certificateDto == null) {
            throw new CertificateNotFoundException(id);
        }

        return certificateDto;
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
