package com.epam.esm.controller.api;

import com.epam.esm.entity.Certificate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RequestMapping("/search")
public interface CertificatesSearchController {



    @RequestMapping(value = "**", method = RequestMethod.GET, produces = "application/json")
    List<Certificate> searchCertificatesByPartOfNameOrDescription(HttpServletRequest request)
            throws UnsupportedEncodingException;

//    @RequestMapping(value = "/{tag}", method = RequestMethod.GET, produces = "application/json")
//    Certificate testSearch(@PathVariable String tag);
}
