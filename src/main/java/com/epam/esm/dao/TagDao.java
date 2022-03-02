package com.epam.esm.dao;

import com.epam.esm.controller.api.dto.TagDownstreamDto;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao {

    boolean create(TagDownstreamDto dto);

    Tag getByName(String name);

    List<Tag> getAll();

    boolean delete(String name);

}
