package com.epam.esm.dao.config;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

//    @Profile("production")
//    @Bean
//    @Scope("singleton")
//    public DataSource dataSource() {
//        return ProductionDatasourceConfig.mysqlDataSource();
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }

//    @Bean
////    @Profile("production")
//    @Scope("singleton")
//    public DataSource dataSource() {
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
