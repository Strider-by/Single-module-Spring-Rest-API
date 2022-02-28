//package com.epam.esm.controller.api.impl;
//
//import com.epam.esm.controller.api.dto.CertificateDownstreamDto;
//import com.epam.esm.controller.config.TestContext;
//import com.epam.esm.controller.config.WebAppContext;
//import com.epam.esm.entity.Certificate;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.entity.dto.Pair;
//import com.epam.esm.service.CertificatesService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
////@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
//@ActiveProfiles("dev")
//@WebAppConfiguration
//class CertificatesControllerImplTestCreation {
//
//    private MockMvc mockMvc;
//    private CertificatesService service;
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//
////    @BeforeAll
////    static void setFields() {
////        System.out.println("CertificatesControllerImplTest.setFields");
////    }
//
//    @BeforeEach
//    void setUp() {
//        service = Mockito.mock(CertificatesService.class);
//        mockMvc = MockMvcBuilders.standaloneSetup(new CertificatesControllerImpl(service))
////                .setHandlerExceptionResolvers(exceptionResolver())
////                .setValidator(new LocalValidatorFactoryBean())
//                //.setViewResolvers(viewResolver())
//                .build();
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//
//    @Test
//    void createCertificate() throws Exception {
//        long id = 1L;
//        String name = "name";
//        int price = 400;
//
//        CertificateDownstreamDto dto = new CertificateDownstreamDto();
//        dto.setName(name);
//        dto.setPrice(price);
//
//        Certificate expected = new Certificate();
//        expected.setId(id);
//        expected.setName(name);
//        expected.setPrice(price);
//        expected.setDuration(33);
//        expected.setCreateDate(new Date());
//        expected.setLastUpdateDate(new Date());
//
//        when(service.createCertificate(dto)).thenReturn(expected);
//        System.out.println("::1::" + service.createCertificate(null));
//        System.out.println("::2::" + service.createCertificate(dto));
//
//        String content = new ContentStringBuilder()
//                .add("name", name)
//                .add("price", price)
//                .build();
//
//        System.out.println("created dto\n" + dto);
//
//        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/certificates")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .characterEncoding("UTF-8")
//                .content(content);
//
//        this.mockMvc.perform(builder)
//                .andExpect(status().isCreated())
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.name").value(name))
//                .andExpect(jsonPath("$.price").value(price));
//
//    }
//
//
//
//    private class ContentStringBuilder {
//        private List<Pair<String, Object>> chunks = new ArrayList<>();
//
//        public ContentStringBuilder add(String name, Object value) {
//            chunks.add(new Pair<>(name, value));
//            return this;
//        }
//
//        public String build() {
//            return chunks.stream()
//                    .map(pair -> String.format("%s=%s", pair.getFirstValue(), pair.getSecondValue().toString()))
//                    .reduce((part1, part2) -> String.join("&", part1, part2))
//                    .orElse("");
//        }
//
//        public void clear() {
//            this.chunks = new ArrayList<>();
//        }
//    }
//
//}