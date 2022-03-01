package com.epam.esm.controller.config;

import com.epam.esm.controller.api.CertificatesController;
import com.epam.esm.controller.api.CertificatesSearchController;
import com.epam.esm.controller.api.TagsController;
import com.epam.esm.controller.api.impl.CertificatesControllerImpl;
import com.epam.esm.controller.api.impl.CertificatesSearchControllerImpl;
import com.epam.esm.controller.api.impl.TagsControllerImpl;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.service.CertificatesService;
import com.epam.esm.service.TagsService;
import com.epam.esm.service.impl.CertificatesServiceImpl;
import com.epam.esm.service.impl.TagsServiceImpl;
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
//@ComponentScan("com.epam.esm")
public class TestContext {

    @Bean
    public CertificatesController certificatesController(CertificatesService service) {
        return new CertificatesControllerImpl(service);
    }

    @Bean
    public CertificatesSearchController certificatesSearchController(CertificatesService service) {
        return new CertificatesSearchControllerImpl(service);
    }

    @Bean
    public TagsController tagsController(TagsService service) {
        return new TagsControllerImpl(service);
    }

    @Bean
    public TagsService tagsService(TagDao dao) {
        return new TagsServiceImpl(dao);
    }

    @Bean
    public TagDao tagDao(JdbcTemplate jdbcTemplate) {
        return new TagDaoImpl(jdbcTemplate);
    }

//    @Bean("certificateControllerMock")
//    public CertificatesController certificatesControllerMock(@Qualifier("certificatesServiceMock") CertificatesService service) {
//        return Mockito.mock(CertificatesController.class);
//    }

//    @Bean
//    public CertificatesService certificatesService(CertificateDao dao) {
//        return new CertificatesServiceImpl(dao);
//    }

//    @Bean("tagsServiceMock")
//    @Scope("prototype")
//    public TagsService tagsServiceMock() {
//        System.out.println("TestContext.tagsServiceMock");
//        return Mockito.mock(TagsService.class);
//    }



//    @Bean("certificatesServiceMock")
//    @Scope("prototype")
//    @Qualifier("certificatesServiceMock")
//    public CertificatesService certificatesServiceMock() {
//        System.out.println("TestContext.certificatesServiceMock");
//        return Mockito.mock(CertificatesService.class);
//    }

//    @Bean
//    @Scope("prototype")
//    public CertificatesController certificatesController(CertificatesService service) {
//        return new CertificatesControllerImpl(service);
//    }

    @Bean
    @Scope("prototype")
    public CertificatesService certificatesService(CertificateDao dao) {
        return new CertificatesServiceImpl(dao);
    }

    @Bean
    public CertificateDao certificateDao(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        return new CertificateDaoImpl(jdbcTemplate, transactionTemplate);
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
