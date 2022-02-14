package com.epam.esm.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm", "com.epam.esm.dao"},
        excludeFilters = {
                @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        })
public class RootConfig {

        static {
                System.out.println("Root config has been created");
        }

}
