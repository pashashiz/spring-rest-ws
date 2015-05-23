package com.ps.tutorial.rest.controllers;

import com.ps.tutorial.rest.data.Greeting;
import com.ps.tutorial.rest.data.GreetingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public Greeting hello(@RequestParam String name) throws GreetingException {
        if (name.isEmpty())
            throw new GreetingException("Name cannot be empty");
        return new Greeting("Hello", name);
    }

    @ExceptionHandler(GreetingException.class)
    public ResponseEntity<GreetingException> handleException(GreetingException exception) {
        return new ResponseEntity<GreetingException>(exception, HttpStatus.NOT_FOUND);
    }

}
