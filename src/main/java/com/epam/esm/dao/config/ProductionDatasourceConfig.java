package com.epam.esm.dao.config;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
//@ComponentScan("com.epam.esm")
public class ProductionDatasourceConfig {

    @Profile("production")
    @Bean
    @Scope("singleton")
    public static DataSource mysqlDataSource() {

        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:mysql://localhost:3306/gift_certificates?allowMultiQueries=true");
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
