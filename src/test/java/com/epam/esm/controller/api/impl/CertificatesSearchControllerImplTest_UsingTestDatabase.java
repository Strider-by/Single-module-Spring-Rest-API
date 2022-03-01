package com.epam.esm.controller.api.impl;

import com.epam.esm.controller.api.CertificatesSearchController;
import com.epam.esm.controller.config.TestContext;
import com.epam.esm.controller.config.WebAppContext;
import com.epam.esm.service.CertificatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
@ActiveProfiles(profiles = "dev")
class CertificatesSearchControllerImplTest_UsingTestDatabase {

    @Autowired
    private CertificatesSearchController controller;
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void searchCertificates_success() throws Exception {
        String searchString = "contains/what";

        mockMvc.perform(get("/search/{searchString}", searchString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void searchCertificates_fail_badRequest_oddParamsQuantity() throws Exception {
        String searchString = "contains/what/wrong";

        mockMvc.perform(get("/search/{searchString}", searchString))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()));
    }

}