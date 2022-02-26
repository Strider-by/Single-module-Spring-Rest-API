package com.epam.esm.controller.config;

import com.epam.esm.controller.api.CertificatesController;
import com.epam.esm.controller.api.impl.CertificatesControllerImpl;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.service.CertificatesService;
import com.epam.esm.service.impl.CertificatesServiceImpl;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
public class TestContext {

    @Bean
    public CertificatesController certificatesController(CertificatesService service) {
        return new CertificatesControllerImpl(service);
    }

    @Bean("certificateControllerMock")
    public CertificatesController certificatesControllerMock(@Qualifier("certificatesServiceMock") CertificatesService service) {
        return Mockito.mock(CertificatesController.class);
    }

//    @Bean
//    public CertificatesService certificatesService(CertificateDao dao) {
//        return new CertificatesServiceImpl(dao);
//    }



    @Bean("certificatesServiceMock")
    @Scope("singleton")
    public CertificatesService certificatesServiceMock() {
        System.out.println("certificatesServiceMock got");
        return Mockito.mock(CertificatesService.class);
    }

    @Bean
    public CertificateDao certificateDao() {
        return new CertificateDaoImpl();
    }

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


}
