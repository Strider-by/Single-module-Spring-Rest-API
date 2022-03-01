package com.epam.esm.controller.api.impl;

import com.epam.esm.ContentStringBuilder;
import com.epam.esm.controller.api.dto.CertificateCreateDto;
import com.epam.esm.controller.config.TestContext;
import com.epam.esm.controller.config.WebAppContext;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
class CertificatesControllerImplTest {

    private MockMvc mockMvc;
    private CertificatesService service;


    @BeforeEach
    void setUp() {
        service = Mockito.mock(CertificatesService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CertificatesControllerImpl(service))
                .build();
    }


    @Test
    void getAllCertificates() throws Exception {
        mockMvc.perform(get("/certificates/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getCertificate_success() throws Exception {
        long id = 1L;
        when(service.getCertificate(id)).thenReturn(new Certificate());

        mockMvc.perform(get("/certificates/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void getCertificate_fail_notFound() throws Exception {
        long id = 404L;
        mockMvc.perform(get("/certificates/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void createCertificate() throws Exception {
        Certificate expected = new Certificate();
        expected.setName("name");
        expected.setDuration(1);
        expected.setCreateDate(new Date());
        expected.setCreateDate(new Date());
        expected.setLastUpdateDate(new Date());
        expected.setLastUpdateDate(new Date());

        when(service.createCertificate(any())).thenReturn(expected);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/certificates")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("UTF-8")
                .content("name=some_name");

        this.mockMvc.perform(builder);
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isCreated());
        //.andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteCertificate_success() throws Exception {
        long id = 1L;
        boolean expected = true;
        when(service.deleteCertificate(id)).thenReturn(expected);
        mockMvc.perform(delete("/certificates/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    void deleteCertificate_fail_notFound() throws Exception {
        long id = 404L;
        boolean expected = false;
        when(service.deleteCertificate(id)).thenReturn(expected);
        mockMvc.perform(delete("/certificates/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void updateCertificate_success() throws Exception {
        long id = 1L;
        String name = "new name";
        int price = 400;

        when(service.updateCertificate(eq(id), any())).thenReturn(new Certificate());

        String content = new ContentStringBuilder()
                .add("name", name)
                .add("price", price)
                .build();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/certificates/{id}", id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("UTF-8")
                .content(content);

        this.mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateCertificate_fail_notFound() throws Exception {
        long id = 404L;
        String name = "new name";
        int price = 400;

        Certificate expected = null;
        when(service.updateCertificate(eq(id), any())).thenReturn(expected);

        String content = new ContentStringBuilder()
                .add("name", name)
                .add("price", price)
                .build();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/certificates/{id}", id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("UTF-8")
                .content(content);


        this.mockMvc.perform(builder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));

    }

}