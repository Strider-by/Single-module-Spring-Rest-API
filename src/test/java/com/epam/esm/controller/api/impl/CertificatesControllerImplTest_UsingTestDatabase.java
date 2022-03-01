package com.epam.esm.controller.api.impl;

import com.epam.esm.ContentStringBuilder;
import com.epam.esm.controller.api.CertificatesController;
import com.epam.esm.controller.config.TestContext;
import com.epam.esm.controller.config.WebAppContext;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.CertificatesService;
import com.epam.esm.service.impl.CertificatesServiceImpl;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
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
@ActiveProfiles(profiles = "dev")
class CertificatesControllerImplTest_UsingTestDatabase {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @Autowired
    private CertificatesController controller;

    private static final int INITIAL_CERTIFICATES_QUANTITY = 4;

    private class CertificateCreateData {
        String name = "Happy walking";
        int duration = 1;
        int price = 1;
        List<String> description = Arrays.asList("walking", "outdoor", "sport");
        String contentString = buildContentString();

        private String buildContentString() {
            ContentStringBuilder contentStringBuilder = new ContentStringBuilder();
            contentStringBuilder.add("name", name)
                    .add("price", price)
                    .add("duration", duration);

            for(String tagName : description) {
                contentStringBuilder.add("description", tagName);
            }
            return contentStringBuilder.build();
        }
    }

    private class CertificateUpdateData {
        String name = "Balloon flights";
        int price = 7;
        int duration = 12;
        List<String> description = Arrays.asList("sky", "flying");
        String contentString = buildContentString();

        private String buildContentString() {
            ContentStringBuilder contentStringBuilder = new ContentStringBuilder();
            contentStringBuilder.add("name", name)
                    .add("price", price)
                    .add("duration", duration);

            for(String tagName : description) {
                contentStringBuilder.add("description", tagName);
            }
            return contentStringBuilder.build();
        }
    }


    @BeforeEach
    void setUp() {

//        service = new CertificatesServiceImpl(dao);
//        controller = new CertificatesControllerImpl(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }


    @Test
    void getAllCertificates() throws Exception {
        mockMvc.perform(get("/certificates/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//                .andExpect(jsonPath("$", hasSize(INITIAL_CERTIFICATES_QUANTITY)));
    }

    @Test
    void getCertificate_success() throws Exception {
        long id = 1L;
        String expectedName = "Swimming pool holidays";
        int expectedPrice = 10;
        int expectedDuration = 2;

        mockMvc.perform(get("/certificates/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedName))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.duration").value(expectedDuration))
                .andExpect(jsonPath("$.description", hasSize(4)))
                .andExpect(jsonPath("$.description[0].name").value("sauna"));
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
        CertificateCreateData data = new CertificateCreateData();
        String contentString = data.contentString;

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/certificates")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(contentString)
                .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(data.name))
                .andExpect(jsonPath("$.price").value(data.price))
                .andExpect(jsonPath("$.duration").value(data.duration))
                .andExpect(jsonPath("$.description", hasSize(data.description.size())));
    }

    @Test
    void deleteCertificate_success() throws Exception {
        long id = 1L;
        mockMvc.perform(delete("/certificates/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andDo(print());
    }

    @Test
    void deleteCertificate_fail_notFound() throws Exception {
        long id = 404L;
        mockMvc.perform(delete("/certificates/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void updateCertificate_success() throws Exception {
        long id = 4L;

        String jsonDataBeforeUpdate = mockMvc.perform(get("/certificates/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

//        System.out.println(jsonDataBeforeUpdate);
        Certificate certificateBeforeUpdate = jsonStringToCertificate(jsonDataBeforeUpdate);
//        System.out.println(certificate);

        CertificateUpdateData certificateUpdateData = new CertificateUpdateData();
        String content = certificateUpdateData.contentString;


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/certificates/{id}", id)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("UTF-8")
                .content(content);

        String jsonDataAfterUpdate = this.mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Certificate certificateAfterUpdate = jsonStringToCertificate(jsonDataAfterUpdate);
        System.out.println(certificateAfterUpdate);

        assertEquals(certificateBeforeUpdate.getId(), certificateAfterUpdate.getId());
        assertEquals(certificateBeforeUpdate.getCreateDate(), certificateAfterUpdate.getCreateDate());
        assertNotEquals(certificateBeforeUpdate.getName(), certificateAfterUpdate.getName());
        assertNotEquals(certificateBeforeUpdate.getPrice(), certificateAfterUpdate.getPrice());
        assertNotEquals(certificateBeforeUpdate.getDuration(), certificateAfterUpdate.getDuration());
        assertNotEquals(certificateBeforeUpdate.getDescription(), certificateAfterUpdate.getDescription());
        assertNotEquals(certificateBeforeUpdate.getLastUpdateDate(), certificateAfterUpdate.getLastUpdateDate());

    }

    @Test
    void updateCertificate_fail_notFound() throws Exception {
        long id = 404L;
        String name = "new name";
        int price = 400;

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

    private Certificate jsonStringToCertificate(String data) {
        Certificate certificate = new Certificate();

        certificate.setId(
                JsonPath.parse(data).read("$.id", Long.class));
        certificate.setPrice(
                JsonPath.parse(data).read("$.price", Integer.class));
        certificate.setDuration(
                JsonPath.parse(data).read("$.duration", Integer.class));
        certificate.setName(
                JsonPath.parse(data).read("$.name"));
        certificate.setCreateDate(
                JsonPath.parse(data).read("$.createDate", Date.class));
        certificate.setLastUpdateDate(
                JsonPath.parse(data).read("$.lastUpdateDate", Date.class));

        List<Map<String, Object>> tagsDataObjects = JsonPath.parse(data).read("$.description[*]");
        List<Tag> tags = tagsDataObjects.stream()
                .map(this::tagPropertiesMapToTag)
                .collect((Collectors.toList()));

        certificate.setDescription(tags);

        System.out.println(tagsDataObjects);
        return certificate;

    }

    private Tag tagPropertiesMapToTag(Map<String, Object> data) {
        long id = Long.parseLong(data.get("id").toString());
        String name = (String) data.get("name");
        return new Tag(id, name);
    }

}