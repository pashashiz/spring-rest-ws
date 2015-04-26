package com.ps.tutorial.rest;

import com.ps.tutorial.rest.data.FacebookPage;
import com.ps.tutorial.rest.data.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RestClient {

    @Autowired
    RestTemplate template;

    public Greeting greet(String name) {
        return template.getForObject("http://localhost:8080/simple-service/hello?name=" + name,
                Greeting.class);
    }

    public FacebookPage getFacebookPage(String uri) {
        return template.getForObject("http://graph.facebook.com/" + uri, FacebookPage.class);
    }

}
