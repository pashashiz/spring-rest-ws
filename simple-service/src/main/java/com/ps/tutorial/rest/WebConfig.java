package com.ps.tutorial.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.ps.tutorial.rest.controllers"})
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Configure custom content negotiation strategy (for json, xml and so on)
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //Json already enabled by default
    }

}
