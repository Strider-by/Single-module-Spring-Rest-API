package com.epam.esm.controller.config;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class WebAppContext {

    @Bean
    @Profile("dev")
    @Scope("singleton")
//    @Scope("prototype")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/initial-data.sql")
                .build();
    }

//    @Bean
//    @Profile("dev")
//    @Scope("singleton")
//    public DataSource mysqlDataSource() {
//
//        PoolProperties p = new PoolProperties();
//        p.setUrl("jdbc:mysql://localhost:3306/gift_certificates?allowMultiQueries=true");
//        p.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        p.setUsername("root");
//        p.setPassword("AEge101");
//        p.setInitialSize(10);
//        p.setMaxActive(100);
//        p.setJdbcInterceptors(
//                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
//                        + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
//        org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
//        datasource.setPoolProperties(p);
//
//        return datasource;
//    }

}
