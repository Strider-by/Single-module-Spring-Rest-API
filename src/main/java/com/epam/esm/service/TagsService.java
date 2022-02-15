package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class TagsService {

    private TagDao tagDao;

    public TagsService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public boolean createTag(String tagName) {
        return tagDao.create(tagName);
    }

    public  boolean deleteTag(String tagName) {
        return tagDao.delete(tagName);
    }

    public List<String> getAllTags() {
        return tagDao.getAll().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }

    public String getTag(String tagName) {
        return tagDao.getByName(tagName).getName();
    }

}
