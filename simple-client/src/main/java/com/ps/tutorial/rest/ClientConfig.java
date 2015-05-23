package com.ps.tutorial.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

    @Bean @Scope("prototype") public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean public RestClient greetingClient() {
        return new RestClient();
    }

}
