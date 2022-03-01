package com.epam.esm.dao.config;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration
public class ProductionDatasourceConfig {

    @Bean
    @Profile("production")
    @Scope("singleton")
    public DataSource mysqlDataSource() {

        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/certificates_for_test?allowMultiQueries=true");
        p.setDriverClassName("com.mysql.cj.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("AEge101");
        p.setInitialSize(10);
        p.setMaxActive(100);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                        + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        datasource.setPoolProperties(p);

        return datasource;
    }

}
