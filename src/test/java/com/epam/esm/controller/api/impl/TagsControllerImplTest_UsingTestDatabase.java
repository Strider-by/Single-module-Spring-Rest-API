package com.epam.esm.controller.api.impl;

import com.epam.esm.util.ContentStringBuilder;
import com.epam.esm.controller.api.TagsController;
import com.epam.esm.controller.config.TestContext;
import com.epam.esm.controller.config.WebAppContext;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
@ActiveProfiles(profiles = "dev")
class TagsControllerImplTest_UsingTestDatabase {

    private MockMvc mockMvc;
    @Autowired
    private TagsController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void getAllTags() throws Exception {
        mockMvc.perform(get("/tags/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getTag_success() throws Exception {
        String name = "water";

        mockMvc.perform(get("/tags/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getTag_fail_notFound() throws Exception {
        String name = "name that does not exist";
        mockMvc.perform(get("/tags/{name}", name))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void createTag() throws Exception {
        TagCreateData data = new TagCreateData();
        String contentString = data.contentString;

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/tags")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(contentString)
                .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createTag_fail_tagAlreadyExists() throws Exception {
        TagCreateData_toBeFailed data = new TagCreateData_toBeFailed();
        String contentString = data.contentString;

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/tags")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(contentString)
                .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()));
    }

    @Test
    void deleteTag_success() throws Exception {
        String name = "outdoor";
        mockMvc.perform(delete("/tags/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()));
    }

    @Test
    void deleteCertificate_fail_notFound() throws Exception {
        String name = "name of the tag that does not exist";
        mockMvc.perform(delete("/tags/{name}", name))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
    }

    private Tag tagPropertiesMapToTag(Map<String, Object> data) {
        long id = Long.parseLong(data.get("id").toString());
        String name = (String) data.get("name");
        return new Tag(id, name);
    }

    private class TagCreateData {

        String name = "Jumping";
        String contentString = buildContentString();

        private String buildContentString() {
            ContentStringBuilder contentStringBuilder = new ContentStringBuilder();
            return contentStringBuilder.add("name", name).build();
        }

    }

    private class TagCreateData_toBeFailed {

        String name = "water";
        String contentString = buildContentString();

        private String buildContentString() {
            ContentStringBuilder contentStringBuilder = new ContentStringBuilder();
            return contentStringBuilder.add("name", name).build();
        }

    }

}