package com.epam.esm.service;

import com.epam.esm.controller.api.dto.TagDownstreamDto;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagsService {

    boolean createTag(TagDownstreamDto dto);

    boolean deleteTag(String tagName);

    List<Tag> getAllTags();

    Tag getTag(String tagName);

}
