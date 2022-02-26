package com.epam.esm.controller.api.impl;

import com.epam.esm.controller.config.TestContext;
import com.epam.esm.controller.config.WebAppContext;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificatesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Arrays;
import java.util.Properties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@ActiveProfiles("dev")
@WebAppConfiguration
class CertificatesControllerImplTest2 {

    private MockMvc mockMvc;
    @Autowired
    @Qualifier("certificatesServiceMock")
    private CertificatesService service;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    @Qualifier("certificateControllerMock")


//    @BeforeAll
//    static void setFields() {
//        System.out.println("CertificatesControllerImplTest.setFields");
//    }

    @BeforeEach
    void setUp() {
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        //mockService = Mockito.mock(CertificatesService.class);
//        mockMvc = MockMvcBuilders.standaloneSetup(new CertificatesControllerImpl(mockService))
//                .setHandlerExceptionResolvers(exceptionResolver())
//                //.setValidator(new LocalValidatorFactoryBean())
//                //.setViewResolvers(viewResolver())
//                .build();
        Mockito.reset(service);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        service = Mockito.mock(CertificatesService.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCertificates() throws Exception {

//        Mockito.when(service.getAllCertificates()).thenReturn(Arrays.asList(new Certificate()));
//
//        MvcResult mvcResult = mockMvc.perform(get("/certificates/"))
//                .andDo(MockMvcResultHandlers.print())/*.andExpect(MockMvcResultMatchers.status().isOk())*/
//                .andReturn();
//
//        System.out.println("local service:\n" + service);
//
//        System.out.println("\n---\n" + mvcResult.getResponse().getContentAsString());
//        mvcResult.getRequest().setCharacterEncoding("UTF-8");
//        System.out.println("\n---\n" + mvcResult.getRequest().getContentAsString());
//        System.out.println("\n---\n" + mvcResult.getResolvedException());


    }

    @Test
    void getCertificate() {
    }

    @Test
    void createCertificate() {
    }

    @Test
    void deleteCertificate() {
    }

    @Test
    void updateCertificate() {
    }



    private HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("com.epam.esm.controller.api.exception.CertificateNotFoundException", "error/404");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

}