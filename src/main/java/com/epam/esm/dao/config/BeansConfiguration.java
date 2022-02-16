package com.epam.esm.dao.config;

import com.epam.esm.controller.api.CertificatesController;
import com.epam.esm.controller.api.TagsController;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.CrossEntityDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.CrossEntityDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.service.CertificatesService;
import com.epam.esm.service.TagsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class BeansConfiguration {

    static {
        System.out.println("BeansConfiguration.static initializer");
    }

    //@Profile("production")
    @Bean
    @Scope("singleton")
    public DataSource dataSource() {
        return ProductionDatasourceConfig.mysqlDataSource();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TagsService tagsService(TagDao tagDao) {
        return  new TagsService(tagDao);
    }

//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(ProductionDatasourceConfig.mysqlDataSource());
//    }

    @Bean
    public CertificateDao certificateDao(JdbcTemplate jdbcTemplate) {
        return new CertificateDaoImpl(jdbcTemplate);
    }

    @Bean
    public TagDao tagDao(JdbcTemplate jdbcTemplate) {
        return new TagDaoImpl(jdbcTemplate);
    }

    @Bean
    public CertificateDaoImpl certificateDaoImpl(JdbcTemplate jdbcTemplate) {
        return new CertificateDaoImpl(jdbcTemplate);
    }

//    @Bean
//    public TagDaoImpl tagDao(JdbcTemplate jdbcTemplate) {
//        return new TagDaoImpl(jdbcTemplate);
//    }

    @Bean
    public CertificatesController certificatesController(CertificatesService certificatesService) {
        return new CertificatesController(certificatesService);
    }

    @Bean
    public TagsController tagsController(TagsService tagsService) {
        return new TagsController(tagsService);
    }

    @Bean
    public CertificatesService certificatesService(CertificateDaoImpl dao) {
        return new CertificatesService(dao);
    }

    @Bean
    public CrossEntityDao crossEntityDao(JdbcTemplate jdbcTemplate) {
        return new CrossEntityDaoImpl(jdbcTemplate);
    }

//    @Bean
//    public CertificateDto certificateDto() {
//        return new CertificateDto();
//    }

}
