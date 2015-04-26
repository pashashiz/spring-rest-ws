package com.ps.tutorial.rest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ClientConfig.class);
        RestClient client = ctx.getBean(RestClient.class);
        System.out.println(client.greet("Pasha"));
        System.out.println(client.getFacebookPage("pivotalsoftware"));
    }

}
