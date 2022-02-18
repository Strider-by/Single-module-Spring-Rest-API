package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class TagDaoImpl implements TagDao {

    private static final String CREATE_INSTANCE =
            "INSERT IGNORE INTO tag (name) VALUES (?);";
    private static final String GET_INSTANCE =
            "SELECT * FROM tag WHERE name = ?";
    private static final String GET_ALL_INSTANCES =
            "SELECT * FROM tag";
    private static final String DELETE_INSTANCE =
            "DELETE FROM tag WHERE name = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(String name) {
        return jdbcTemplate.update(CREATE_INSTANCE, name) == 1;
    }

    @Override
    public Tag getByName(String name) {
        try {
            return jdbcTemplate.queryForObject(
                    GET_INSTANCE,
                    new TagRowMapper(),
                    name
            );
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(
                GET_ALL_INSTANCES,
                new TagRowMapper()
        );
    }

    @Override
    public boolean delete(String name) {
        return jdbcTemplate.update(DELETE_INSTANCE, name) == 1;
    }



    private static class TagRowMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tag tag = new Tag();
            tag.setName(rs.getString("name"));
            tag.setId(rs.getInt("id"));
            return tag;
        }

    }

}
