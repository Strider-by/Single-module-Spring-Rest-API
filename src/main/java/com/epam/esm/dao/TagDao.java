package com.epam.esm.dao;

import com.epam.esm.controller.api.dto.TagDownstreamDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public interface TagDao {

    boolean create(TagDownstreamDto dto);

    Tag getByName(String name);

    List<Tag> getAll();

    boolean delete(String name);

}
