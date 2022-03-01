package com.epam.esm.controller.api.impl;

import com.epam.esm.controller.api.dto.CertificateCreateDto;
import com.epam.esm.controller.config.TestContext;
import com.epam.esm.controller.config.WebAppContext;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.dto.Pair;
import com.epam.esm.service.CertificatesService;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@ActiveProfiles("dev")
@WebAppConfiguration
class CertificatesControllerImplTestExample {

    private MockMvc mockMvc;
    private CertificatesService service;
    @Autowired
    private WebApplicationContext webApplicationContext;


//    @BeforeAll
//    static void setFields() {
//        System.out.println("CertificatesControllerImplTest.setFields");
//    }

    @BeforeEach
    void setUp() {
        service = Mockito.mock(CertificatesService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CertificatesControllerImpl(service))
//                .setHandlerExceptionResolvers(exceptionResolver())
//                .setValidator(new LocalValidatorFactoryBean())
                //.setViewResolvers(viewResolver())
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCertificates() throws Exception {

        List<Certificate> expected = Arrays.asList(new Certificate(), new Certificate());
        when(service.getAllCertificates()).thenReturn(expected);

        mockMvc.perform(get("/certificates/"))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expected.size())));
//
//
//        System.out.println("\n---\n" + mvcResult.getResponse().getContentAsString());
//        mvcResult.getRequest().setCharacterEncoding("UTF-8");
//        System.out.println("\n---\n" + mvcResult.getRequest().getContentAsString());
//        System.out.println("\n---\n" + mvcResult.getResolvedException());
    }

    @Test
    void getCertificate_success() throws Exception {
        long id = 1L;
        String name = "generic name";
        int price = 100;
        int duration = 3;
        Date created = new Date();
        Date updated = new Date();
        List<Tag> description = Arrays.asList(new Tag(), new Tag(), new Tag());

        Certificate expected = new Certificate();
        expected.setId(id);
        expected.setName(name);
        expected.setCreateDate(created);
        expected.setLastUpdateDate(updated);
        expected.setPrice(price);
        expected.setDuration(duration);
        expected.setDescription(description);

        when(service.getCertificate(id)).thenReturn(expected);

        mockMvc.perform(get("/certificates/{id}", id))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.duration").value(duration))
                .andExpect(jsonPath("$.createDate").value(created))
                .andExpect(jsonPath("$.lastUpdateDate").value(updated))
                .andExpect(jsonPath("$.description", hasSize(description.size())));

    }

    @Test
    void getCertificate_notFound() throws Exception {
        long id = 404L;
        mockMvc.perform(get("/certificates/{id}", id))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
//                .andExpect(jsonPath("$.code").value(errorCode));
//        CertificateNotFoundException exception = Assertions.assertThrows(CertificateNotFoundException.class,
//                () -> {
//                    mockMvc.perform(get("/certificates/{id}", id))
////                .andDo(MockMvcResultHandlers.print())
//                            .andExpect(status().isNotFound());
//
//                });
    }

    @Test
    void createCertificate() throws Exception {
        long id = 1L;
        String name = "new_name";
        int price = 400;

        CertificateCreateDto dto = new CertificateCreateDto();
        dto.setName(name);
        dto.setPrice(price);

        Certificate expected = new Certificate();
        expected.setId(id);
        expected.setName(name);
        expected.setPrice(price);
        expected.setDuration(33);
        expected.setCreateDate(new Date());
        expected.setLastUpdateDate(new Date());

        when(service.createCertificate(dto)).thenReturn(expected);
        System.out.println(service.createCertificate(dto));

        String content = new ContentStringBuilder()
                .add("name", name)
                .add("price", price)
                .build();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/certificates")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("UTF-8")
                .content(content);


        this.mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.price").value(price));
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
    void deleteCertificate_notFound() throws Exception {
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

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", name);
        params.add("price", Integer.toString(price));

        Certificate expected = new Certificate();
        expected.setId(id);
        expected.setName(name);
        expected.setPrice(price);
//        expected.setId(id);

        when(service.updateCertificate(id, params)).thenReturn(expected);

        String content = new ContentStringBuilder()
                .add("name", name)
                .add("price", price)
                .build();


        // String.format("name=%s&price=%d", name, price)
//        mockMvc.perform(patch("/certificates/{id}", id))
//                .content()
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/certificates/{id}", id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                        .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .characterEncoding("UTF-8")
                .content(content);


        this.mockMvc.perform(builder)
                .andExpect(status().isOk())
                //.andExpect(content().string("Article created."))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.price").value(price));


    }

    @Test
    void updateCertificate_notFound() throws Exception {
        long id = 404L;
        String name = "new name";
        int price = 400;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", name);
        params.add("price", Integer.toString(price));

        Certificate expected = null;

        when(service.updateCertificate(id, params)).thenReturn(expected);

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




//    private HandlerExceptionResolver exceptionResolver() {
//        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
//
//        Properties exceptionMappings = new Properties();
//
//        exceptionMappings.put("com.epam.esm.controller.api.exception.CertificateNotFoundException", "error/404");
//        exceptionMappings.put("java.lang.Exception", "error/error");
//        exceptionMappings.put("java.lang.RuntimeException", "error/error");
//
//        exceptionResolver.setExceptionMappings(exceptionMappings);
//
//        Properties statusCodes = new Properties();
//
//        statusCodes.put("error/404", "404");
//        statusCodes.put("error/error", "500");
//
//        exceptionResolver.setStatusCodes(statusCodes);
//
//        return exceptionResolver;
//    }

    private class ContentStringBuilder {
        private List<Pair<String, Object>> chunks = new ArrayList<>();

        public ContentStringBuilder add(String name, Object value) {
            chunks.add(new Pair<>(name, value));
            return this;
        }

        public String build() {
            return chunks.stream()
                    .map(pair -> String.format("%s=%s", pair.getFirstValue(), pair.getSecondValue().toString()))
                    .reduce((part1, part2) -> String.join("&", part1, part2))
                    .orElse("");
        }

        public void clear() {
            this.chunks = new ArrayList<>();
        }
    }

}