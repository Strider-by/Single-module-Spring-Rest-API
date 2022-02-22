package com.epam.esm.controller.api;

import com.epam.esm.controller.api.exception.BadRequestParametersException;
import com.epam.esm.controller.api.exception.CertificateNotFoundException;
import com.epam.esm.controller.util.Message;
import com.epam.esm.entity.dto.CertificateDto;
import com.epam.esm.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@RestController
@RequestMapping("/certificates/search")
public class CertificatesSearchController {


    @Autowired
    private CertificatesService certificatesService;

//    static {
//        System.out.println("CertificatesSearchController.static initializer");
//    }

    public CertificatesSearchController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }


    @RequestMapping(value="**", method=RequestMethod.GET, produces="application/json")
    public List<CertificateDto> searchCertificatesByPartOfNameOrDescription(HttpServletRequest request)
            throws UnsupportedEncodingException{

//        System.out.println("CertificatesSearchController.getAllCertificates");
        String[] uriParts = request.getRequestURI().split("/");
        int partsToSkip = 3;

        String[] actualParametersPart = new String[uriParts.length - partsToSkip];
        System.arraycopy(uriParts, partsToSkip, actualParametersPart, 0, actualParametersPart.length);

        if (actualParametersPart.length % 2 != 0) {
            throw new BadRequestParametersException("Quantity of parameters names and values must be equal");
        }

        Map<String, String> parameters = new HashMap<>();
        for (int i = 0; i < actualParametersPart.length - 1; i += 2) {
            String paramName = URLDecoder.decode(actualParametersPart[i].toLowerCase(), "utf-8");
            String paramValue = URLDecoder.decode(actualParametersPart[i+1].toLowerCase(), "utf-8");

            boolean isValidParameter = SearchParameterVerifier.isAllowedNameValueParameter(paramName, paramValue);
            if (!isValidParameter) {
                throw new BadRequestParametersException(
                        String.format("Invalid name - value parameter pair: [%s] = [%s]", paramName, paramValue));
            }

            parameters.put(paramName, paramValue);

        }

        //return certificatesService.searchCertificates(parameters);
        List<CertificateDto> certificateDtos = certificatesService.searchCertificates(parameters);
//        System.out.println(certificateDtos);
        return certificateDtos;
    }

    @ExceptionHandler(BadRequestParametersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message certificateNotFound(BadRequestParametersException ex) {
        return new Message(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private enum SearchParameterVerifier {

        TAG ("tag") {
            @Override
            public boolean isAllowedParameterValue(String value) {
                return true;
            }
        },

        CONTAINS ("contains") {
            @Override
            public boolean isAllowedParameterValue(String value) {
                return true;
            }
        },

        SORT_BY ("sort_by", Arrays.asList("date", "name")) {
            @Override
            public boolean isAllowedParameterValue(String value) {
                return this.allowedParamValues.contains(value.toLowerCase());
            }
        },

        ORDER ("order", Arrays.asList("asc", "desc")) {
            @Override
            public boolean isAllowedParameterValue(String value) {
                return this.allowedParamValues.contains(value.toLowerCase());
            }
        },

        DEFAULT ("") {
            @Override
            public boolean isAllowedParameterValue(String value) {
                return false;
            }
        };

        String paramName;
        List<String> allowedParamValues;

        SearchParameterVerifier(String paramName) {
            this.paramName = paramName;
        }

        SearchParameterVerifier(String paramName, List<String> allowedParamValues) {
            this.paramName = paramName;
            this.allowedParamValues = allowedParamValues;
        }

        public abstract boolean isAllowedParameterValue(String value);

        public static boolean isAllowedNameValueParameter(String paramName, String paramValue) {
            return getParameterVerifier(paramName).isAllowedParameterValue(paramValue);
        }

        private static SearchParameterVerifier getParameterVerifier(String paramName) {
            return Arrays.stream(values())
                    .filter(verifier -> verifier.paramName.equalsIgnoreCase(paramName))
                    .findAny()
                    .orElse(DEFAULT);
        }

    }
}
