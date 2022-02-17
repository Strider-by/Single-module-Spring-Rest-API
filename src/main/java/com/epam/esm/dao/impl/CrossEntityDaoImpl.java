//package com.epam.esm.dao.impl;
//
//import com.epam.esm.dao.CrossEntityDao;
//import com.epam.esm.dto.CertificateDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class CrossEntityDaoImpl implements CrossEntityDao {
//
//    private static final String CREATE_CERTIFICATE_AND_GET_ITS_ID =
//            "INSERT INTO gift_certificate (name, price, duration, create_date, last_update_date) "
//                    + "VALUES (?, ?, ?, ?, ?);"
//                    + "SELECT LAST_INSERT_ID();";
//
//    private static final String CREATE_TAGS_AND_BOUND_THEM_TO_CERTIFICATE_TEMPLATE_MASK =
//            "INSERT IGNORE INTO tag (name) VALUES %s; \n"
//                    + "INSERT INTO tags_to_certificates (certificate_id, tag_id) "
//                    + "SELECT ?, tag.id FROM tag WHERE tag.name IN (%s);";
//
//    private static final String VALUE_MASK_IN_BRACKETS = "(?)";
//    private static final String SIMPLE_VALUE_MASK = "?";
//    private static final String VALUES_DELIMITER = ", ";
//
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public CrossEntityDaoImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public CertificateDto createCertificate(CertificateDto dto) {
//        long certificateId = jdbcTemplate.queryForObject(CREATE_CERTIFICATE_AND_GET_ITS_ID, Long.class);
//        List<String> tagsNames = dto.getDescription();
//
//        if (!tagsNames.isEmpty()) {
//            String createAndBoundTagsQuery = buildCreateTagsQuery(tagsNames.size());
//            Object[] arguments = Stream.of(tagsNames.toArray(), new Object[] {certificateId}, tagsNames.toArray())
//                    .flatMap(Arrays::stream)
//                    .toArray();
//            jdbcTemplate.update(createAndBoundTagsQuery, arguments);
//        }
//
//        dto.setId(certificateId);
//        return dto;
//    }
//
//    private String buildCreateTagsQuery(int tagsToCreate) {
//        String valuePlaceholders1 = multiplyAndJoin(VALUE_MASK_IN_BRACKETS, VALUES_DELIMITER, tagsToCreate);
//        String valuePlaceholders2 = multiplyAndJoin(SIMPLE_VALUE_MASK, VALUES_DELIMITER, tagsToCreate);
//        return String.format(CREATE_TAGS_AND_BOUND_THEM_TO_CERTIFICATE_TEMPLATE_MASK, valuePlaceholders1, valuePlaceholders2);
//    }
//
//    private String multiplyAndJoin(String string, String delimiter, int count) {
//        return Arrays.stream(new String[count])
//                .map(s -> string)
//                .collect(Collectors.joining(delimiter));
//    }
//
//}
