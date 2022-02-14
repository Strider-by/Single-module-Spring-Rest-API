package com.epam.esm.dao;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(ProductionDatasourceConfig.mysqlDataSource());
//    }

    @Bean
    public CertificateDao certificateDao(JdbcTemplate jdbcTemplate) {
        return new CertificateDao(jdbcTemplate);
    }

//    @Bean
//    public CertificateDto certificateDto() {
//        return new CertificateDto();
//    }

}
