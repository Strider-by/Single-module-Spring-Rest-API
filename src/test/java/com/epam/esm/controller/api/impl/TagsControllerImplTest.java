package com.epam.esm.controller.api.impl;

import com.epam.esm.controller.api.dto.TagDownstreamDto;
import com.epam.esm.controller.config.TestContext;
import com.epam.esm.controller.config.WebAppContext;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
@ActiveProfiles(profiles = "dev")
class TagsControllerImplTest {

    private MockMvc mockMvc;
    private TagsService service;


    @BeforeEach
    void setUp() {
        service = Mockito.mock(TagsService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new TagsControllerImpl(service))
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
        String name = "tag name";
        when(service.getTag(name)).thenReturn(new Tag());
        mockMvc.perform(get("/tags/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getTag_fail_notFound() throws Exception {
        String name = "tag name";
        when(service.getTag(name)).thenReturn(null);
        mockMvc.perform(get("/tags/{name}", name))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void createTag_success() throws Exception {
        when(service.createTag(any(TagDownstreamDto.class))).thenReturn(true);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/tags")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createTag_fail_tagAlreadyExists() throws Exception {
        when(service.createTag(any(TagDownstreamDto.class))).thenReturn(false);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/tags")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()));
    }

    @Test
    void deleteTag_success() throws Exception {
        String name = "tag name";
        boolean tagDeleted = true;
        when(service.deleteTag(name)).thenReturn(tagDeleted);
        mockMvc.perform(delete("/tags/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()));
    }

    @Test
    void deleteTag_fail_notFound() throws Exception {
        String name = "tag name";
        boolean tagDeleted = false;
        when(service.deleteTag(name)).thenReturn(tagDeleted);
        mockMvc.perform(delete("/tags/{name}", name))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
    }

}