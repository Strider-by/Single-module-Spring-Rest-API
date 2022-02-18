package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.CertificateUpdateDto;
import com.epam.esm.dto.Pair;
import com.epam.esm.entity.Certificate;
import com.epam.esm.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CertificateDaoImpl implements CertificateDao {

    private static final String CHECK_IF_CERTIFICATE_EXISTS =
            "SELECT COUNT(id) FROM gift_certificate WHERE id = ? > 0";

    private static final String GET_CERTIFICATE =
            "SELECT * FROM gift_certificate WHERE id = ?";

    private static final String GET_TAGS_BOUND_TO_CERTIFICATE =
            "SELECT tag.name FROM tags_to_certificates ttc LEFT JOIN tag "
                    + "ON ttc.tag_id = tag.id "
                    + "WHERE certificate_id = ?";

    private static final String GET_ALL_TAGS_TO_CERTIFICATES_BOUNDS =
            "SELECT DISTINCT ttc.certificate_id, tag.name AS tag_name "
                    + "FROM tags_to_certificates ttc LEFT JOIN tag "
                    + "ON tag.id = ttc.tag_id;";

    private static final String GET_ALL_CERTIFICATES =
            "SELECT * FROM gift_certificate";


    private static final String CREATE_CERTIFICATE =
            "INSERT INTO gift_certificate (name, price, duration, create_date, last_update_date) "
                    + "VALUES (?, ?, ?, ?, ?);";

    private static final String GET_LAST_CREATED_ID =
            "SELECT LAST_INSERT_ID();";

    private static final String CREATE_TAGS_AND_BOUND_THEM_TO_CERTIFICATE_TEMPLATE_MASK =
            "INSERT IGNORE INTO tag (name) VALUES %s; \n"
                    + "INSERT INTO tags_to_certificates (certificate_id, tag_id) "
                    + "SELECT ?, tag.id FROM tag WHERE tag.name IN (%s);";

    private static final String UPDATE_CERTIFICATE_TEMPLATE_MASK =
            "UPDATE gift_certificate SET %s WHERE id = ?";

    private static final String DELETE_TAGS_TO_CERTIFICATE_BOUNDS =
            "DELETE FROM tags_to_certificates WHERE certificate_id = ?; ";

    private static final String DELETE_CERTIFICATE =
            "DELETE FROM gift_certificate WHERE id = ?";

    private static final String UPDATE_FIELD_VALUE_TEMPLATE_MASK = "%s = ?";
    private static final String VALUE_MASK_IN_BRACKETS = "(?)";
    private static final String SIMPLE_VALUE_MASK = "?";
    private static final String COMMA_DELIMITER = ", ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CertificateDto createCertificate(Certificate certificate, List<String> tagsNames) {
        jdbcTemplate.update(CREATE_CERTIFICATE,
                certificate.getName(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate()
        );

        long certificateId = jdbcTemplate.queryForObject(GET_LAST_CREATED_ID, Long.class);
        createAndBindTags(certificateId, tagsNames);
        return getCertificateById(certificateId);
    }

    @Override
    public CertificateDto getCertificateById(long id) {

        try {
            Certificate certificate =  jdbcTemplate.queryForObject(
                    GET_CERTIFICATE,
                    new CertificateRowMapper(),
                    id
            );
            CertificateDto certificateDto = DtoConverter.toCertificateDto(certificate);
            List<String> tagNames = jdbcTemplate.queryForList(GET_TAGS_BOUND_TO_CERTIFICATE, String.class, id);
            certificateDto.setDescription(tagNames);
            return certificateDto;

        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        }
    }

    private boolean checkIfCertificateExists(long id) {
        return jdbcTemplate.queryForObject(CHECK_IF_CERTIFICATE_EXISTS, Boolean.class, id);
    }

    @Override
    public List<CertificateDto> getAllCertificates() {

        TreeMap<Long, CertificateDto> certificateDtos = jdbcTemplate.query(GET_ALL_CERTIFICATES, new CertificateRowMapper()).stream()
                .map(DtoConverter::toCertificateDto)
                .collect(Collectors.toMap(certificate -> certificate.getId(), Function.identity(), (o1, o2) -> o1, TreeMap::new));

        Map<Long, List<String>> tagsToCertificatesBounds =
                jdbcTemplate.query(GET_ALL_TAGS_TO_CERTIFICATES_BOUNDS, new TagToCertificateRowMapper()).stream()
                .collect(Collectors.groupingBy(Pair::getFirstValue,
                        Collectors.mapping(Pair::getSecondValue, Collectors.toList())));

        for (Map.Entry<Long, List<String>> entry : tagsToCertificatesBounds.entrySet()) {
            long certificateId = entry.getKey();
            certificateDtos.get(certificateId).setDescription(entry.getValue());
        }

        return Stream.of(certificateDtos)
                .map(Map::values)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

    }

    @Override
    public CertificateDto update(CertificateUpdateDto dto) {

        if (!checkIfCertificateExists(dto.getId())) {
            return null;
        }

        String name;
        Integer price;
        Integer duration;
        Date lastUpdate;
        List<String> parametersToChange = new ArrayList<>();
        if (Objects.nonNull(name = dto.getName())) parametersToChange.add("name");
        if (Objects.nonNull(price = dto.getPrice())) parametersToChange.add("price");
        if (Objects.nonNull(duration = dto.getDuration())) parametersToChange.add("duration");
        parametersToChange.add("last_update_date");
        lastUpdate = dto.getLastUpdate();
        String queryString = buildUpdateCertificateQuery(parametersToChange.toArray(new String[0]));
        Object[] params = Stream.of(name, price, duration, lastUpdate, dto.getId())
                .filter(Objects::nonNull)
                .toArray();

        jdbcTemplate.update(queryString, params);
        replaceTagsOnCertificateUpdate(dto);
        return getCertificateById(dto.getId());
    }

    private void replaceTagsOnCertificateUpdate(CertificateUpdateDto dto) {
        List<String> tags = dto.getDescription();
        if (Objects.nonNull(tags)) {
            jdbcTemplate.update(DELETE_TAGS_TO_CERTIFICATE_BOUNDS, dto.getId());
            createAndBindTags(dto.getId(), tags);
        }
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(DELETE_CERTIFICATE, id) == 1;
    }

    private void createAndBindTags(long certificateId, List<String> tagsNames) {
        if (!tagsNames.isEmpty()) {
            String createAndBoundTagsQuery = buildCreateTagsQuery(tagsNames.size());
            Object[] arguments = Stream.of(tagsNames.toArray(), new Object[]{certificateId}, tagsNames.toArray())
                    .flatMap(Arrays::stream)
                    .toArray();
            System.out.println(Arrays.toString(arguments));
            jdbcTemplate.update(createAndBoundTagsQuery, arguments);
        }
    }

    private String buildUpdateCertificateQuery(String ... fields) {
        String fieldsValuesPartTemplate = multiplyAndJoin(UPDATE_FIELD_VALUE_TEMPLATE_MASK, COMMA_DELIMITER, fields.length);
        String fieldsValuesPart = String.format(fieldsValuesPartTemplate, fields);
        String queryString = String.format(UPDATE_CERTIFICATE_TEMPLATE_MASK, fieldsValuesPart);
        System.out.println(queryString);
        return queryString;
    }

    private String buildCreateTagsQuery(int tagsToCreate) {
        String valuePlaceholders1 = multiplyAndJoin(VALUE_MASK_IN_BRACKETS, COMMA_DELIMITER, tagsToCreate);
        String valuePlaceholders2 = multiplyAndJoin(SIMPLE_VALUE_MASK, COMMA_DELIMITER, tagsToCreate);
        System.out.println(String.format(CREATE_TAGS_AND_BOUND_THEM_TO_CERTIFICATE_TEMPLATE_MASK, valuePlaceholders1, valuePlaceholders2));
        return String.format(CREATE_TAGS_AND_BOUND_THEM_TO_CERTIFICATE_TEMPLATE_MASK, valuePlaceholders1, valuePlaceholders2);
    }

    private String multiplyAndJoin(String string, String delimiter, int count) {
        return Arrays.stream(new String[count])
                .map(s -> string)
                .collect(Collectors.joining(delimiter));
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

    private static class TagToCertificateRowMapper implements RowMapper<Pair<Long, String>> {

        @Override
        public Pair<Long, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Pair<Long, String> pair = new Pair<>();
            pair.setFirstValue(rs.getLong("certificate_id"));
            pair.setSecondValue(rs.getString("tag_name"));

            return pair;
        }

    }

}
