package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao {

    boolean create(String name);

    Tag getByName(String name);

    List<Tag> getAll();

    boolean delete(String name);

}
