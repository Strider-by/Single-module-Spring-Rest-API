//package com.epam.esm.dao;
//
//import org.apache.tomcat.jdbc.pool.DataSource;
//import org.apache.tomcat.jdbc.pool.PoolProperties;
//
//public class TomcatPoolExample  {
//
//    public static javax.sql.DataSource configDataSource() {
//        PoolProperties p = new PoolProperties();
//        p.setUrl("jdbc:mysql://localhost:3306/gift_certificates");
//        p.setDriverClassName("com.mysql.jdbc.Driver");
//        p.setUsername("root");
//        p.setPassword("AEge101");
//        p.setInitialSize(10);
//        p.setMaxActive(100);
//        p.setJdbcInterceptors(
//                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
//                        + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
//        DataSource datasource = new DataSource();
//        datasource.setPoolProperties(p);
//
//        return datasource;
//    }
//}
