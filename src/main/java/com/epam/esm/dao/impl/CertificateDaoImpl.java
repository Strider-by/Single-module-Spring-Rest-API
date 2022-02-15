package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CertificateDaoImpl implements CertificateDao {

    private static final String CREATE_INSTANCE =
            "INSERT INTO gift_certificate (name, price, duration, create_date, last_update_date) "
                    + "VALUES (?, ?, ?, ?, ?);";
    private static final String GET_INSTANCE =
            "SELECT * FROM gift_certificate WHERE id = ?";
    private static final String GET_ALL_INSTANCES =
            "SELECT * FROM gift_certificate";
    private static final String DELETE_INSTANCE =
            "DELETE FROM gift_certificate WHERE id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public CertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long create(Certificate certificate) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("gift_certificate")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", certificate.getName());
        parameters.put("price", certificate.getPrice());
        parameters.put("duration", certificate.getDuration());
        parameters.put("create_date", certificate.getCreateDate());
        parameters.put("last_update_date", certificate.getLastUpdateDate());
        long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return id;
    }

//    public Certificate create_old(Certificate certificate) {
//
//        certificate.affixCreateTimestamp();
//        certificate.setLastUpdateDate(certificate.getCreateDate());
//        //SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//
//
//        jdbcTemplate.update(
//                CREATE_INSTANCE,
//                certificate.getName(),
//                certificate.getPrice(),
//                certificate.getDuration(),
//                certificate.getCreateDate().toInstant().toEpochMilli(),
//                certificate.getLastUpdateDate().toInstant().toEpochMilli()
//        );
//        return certificate;
//    }

    @Override
    public Certificate getById(long id) {
        return jdbcTemplate.queryForObject(
                GET_INSTANCE,
                new CertificateRowMapper(),
                id
        );
    }

    @Override
    public List<Certificate> getAll() {
        return jdbcTemplate.query(
                GET_ALL_INSTANCES,
                new CertificateRowMapper()
        );
    }

    @Override
    public Certificate update(Map<String, Object> nameValuePairs) {
        // todo: fix
        return null;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(DELETE_INSTANCE, id) == 1;
    }


    private static class CertificateRowMapper implements RowMapper<Certificate> {

        @Override
        public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
            Certificate certificate = new Certificate();
            certificate.setId(rs.getLong("id"));
            certificate.setName(rs.getString("name"));
            certificate.setPrice(rs.getInt("price"));
            certificate.setDuration(rs.getInt("duration"));
            certificate.setCreateDate(rs.getTimestamp("create_date"));
            certificate.setLastUpdateDate(rs.getTimestamp("last_update_date"));

            return certificate;
        }

    }

}
