package com.epam.esm.service.impl;

import com.epam.esm.controller.api.dto.TagDownstreamDto;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TagsServiceImplTest {

    TagsService service;
    @Mock
    private TagDao dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TagsServiceImpl(dao);
    }


    @Test
    void createTag_success() {
        boolean expected = true;
        TagDownstreamDto dto = new TagDownstreamDto();
        when(dao.create(dto)).thenReturn(expected);
        boolean actual = service.createTag(dto);

        assertEquals(expected, actual);
    }

    @Test
    void createTag_fail() {
        boolean expected = false;
        TagDownstreamDto dto = new TagDownstreamDto();
        when(dao.create(dto)).thenReturn(expected);
        boolean actual = service.createTag(dto);

        assertEquals(expected, actual);
    }

    @Test
    void deleteTag_success() {
        boolean expected = true;
        String tagName = "example tag name";
        when(dao.delete(tagName)).thenReturn(expected);
        boolean actual = service.deleteTag(tagName);

        assertEquals(expected, actual);
    }

    @Test
    void getAllTags() {
        List<Tag> expected = Arrays.asList(new Tag(), new Tag(), new Tag());
        when(dao.getAll()).thenReturn(expected);
        List<Tag> actual = service.getAllTags();

        assertEquals(expected, actual);
    }

    @Test
    void getTag_success() {
        String tagName = "tag name";
        Tag expected = new Tag();
        when(dao.getByName(tagName)).thenReturn(expected);
        Tag actual = service.getTag(tagName);

        assertEquals(expected, actual);
    }

    @Test
    void getTag_fail() {
        String tagName = "tag name";
        Tag expected = null;
        when(dao.getByName(tagName)).thenReturn(expected);
        Tag actual = service.getTag(tagName);

        assertEquals(expected, actual);
    }

}