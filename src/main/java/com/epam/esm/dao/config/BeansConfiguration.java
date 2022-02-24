package com.epam.esm.dao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

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
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(transactionManager());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        return transactionManager;
    }


//    @Bean
//    public TagsService tagsService(TagDao tagDao) {
//        return  new TagsService(tagDao);
//    }


//    @Bean
//    public CertificateDao certificateDao(JdbcTemplate jdbcTemplate) {
//        return new CertificateDaoImpl(jdbcTemplate);
//    }

//    @Bean
//    public TagDao tagDao(JdbcTemplate jdbcTemplate) {
//        return new TagDaoImpl(jdbcTemplate);
//    }

//    @Bean
//    public CertificateDaoImpl certificateDaoImpl(JdbcTemplate jdbcTemplate) {
//        return new CertificateDaoImpl(jdbcTemplate);
//    }

//    @Bean
//    public CertificatesController certificatesController(CertificatesService certificatesService) {
//        return new CertificatesController(certificatesService);
//    }
//
//    @Bean
//    public TagsController tagsController(TagsService tagsService) {
//        return new TagsController(tagsService);
//    }

//    @Bean
//    public CertificatesService certificatesService(CertificateDaoImpl dao) {
//        return new CertificatesService(dao);
//    }

}
