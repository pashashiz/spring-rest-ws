package com.ps.tutorial.rest.controllers;

import com.ps.tutorial.rest.data.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public String documentation() {
        return "Rest-WS documentation...";
    }

    @RequestMapping("/hello")
    public Greeting hello(@RequestParam String name) {
        return new Greeting("Hello", name);
    }

}
