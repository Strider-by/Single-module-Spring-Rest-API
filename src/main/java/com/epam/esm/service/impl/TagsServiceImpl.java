package com.epam.esm.service.impl;

import com.epam.esm.controller.api.dto.TagDownstreamDto;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagsServiceImpl implements TagsService {

    private TagDao tagDao;

    public TagsServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public boolean createTag(TagDownstreamDto dto) {
        return tagDao.create(dto);
    }

    @Override
    public  boolean deleteTag(String tagName) {
        return tagDao.delete(tagName);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.getAll();
    }

    @Override
    public Tag getTag(String tagName) {
        return tagDao.getByName(tagName);
    }

}
