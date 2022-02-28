package com.epam.esm.controller.api.impl;

import com.epam.esm.controller.config.TestContext;
import com.epam.esm.controller.config.WebAppContext;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
class CertificatesSearchControllerImplTest {

    private MockMvc mockMvc;
    private CertificatesService service;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(CertificatesService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CertificatesControllerImpl(service))
                .build();
    }

    @Test
    void searchCertificatesByPartOfNameOrDescription() throws Exception {
        String params = "some/params/to/be/used/to/search";
        List<Certificate> expected = Arrays.asList(new Certificate(), new Certificate());
        when(service.searchCertificates(any(Map.class))).thenReturn(expected);

        mockMvc.perform(get("/search/{tag}", "tag"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}