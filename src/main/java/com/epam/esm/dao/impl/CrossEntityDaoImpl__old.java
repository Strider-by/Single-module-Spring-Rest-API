//package com.epam.esm.dao.impl;
//
//import com.epam.esm.dao.CrossEntityDao;
//
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//public class CrossEntityDaoImpl__old implements CrossEntityDao {
//
//    private static final String CREATE_TAGS_TEMPLATE_PT_1 =
//            "INSERT IGNORE INTO tag (name) VALUES ";
//    private static final String CREATE_CERTIFICATE_AND_GET_ITS_ID =
//            "INSERT INTO gift_certificate (name, price, duration, create_date, last_update_date) "
//                    + "VALUES (?, ?, ?, ?, ?);"
//                    + "SELECT LAST_INSERT_ID();";
//    private static final String GET_SELECTED_TAGS_IDS_STRING_FORMAT_MASK =
//            "SELECT tag.id FROM tag WHERE tag.name IN (%s)";
//
//    private static final String CREATION_TEMPLATE_VALUE_MASK = "(?)";
//    private static final String SIMPLE_VALUE_MASK = "?";
//    private static final String VALUES_DELIMITER = ", ";
//
//
//
//
//    private String buildCreateTagsQuery(int tagsToCreate) {
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(CREATE_TAGS_TEMPLATE_PT_1);
//        String valuePlaceholders = multiplyAndJoin(CREATION_TEMPLATE_VALUE_MASK, VALUES_DELIMITER, tagsToCreate);
//        sb.append(valuePlaceholders);
//
//        return sb.toString();
//    }
//
//    private String buildGetSelectedTagsIdsQuery(int tagsToGetIds) {
//        String valuePlaceholders = multiplyAndJoin(SIMPLE_VALUE_MASK, VALUES_DELIMITER, tagsToGetIds);
//        return String.format(GET_SELECTED_TAGS_IDS_STRING_FORMAT_MASK, valuePlaceholders);
//    }
//
//    private String multiplyAndJoin(String string, String delimiter, int count) {
//        return Arrays.stream(new String[count])
//                .map(s -> string)
//                .collect(Collectors.joining(delimiter));
//    }
//
//}
