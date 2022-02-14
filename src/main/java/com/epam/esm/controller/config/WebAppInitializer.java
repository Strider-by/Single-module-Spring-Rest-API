package com.epam.esm.controller.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Controller
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    static {
        System.out.println("WebAppInitializer has been created");
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

}
