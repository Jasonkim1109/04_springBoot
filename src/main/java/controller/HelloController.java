package com.example.wonit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String sayHello(@RequestParam(value = "name", defaultValue = "guest") String name) {
        return "Hello, Spring Boot! " + name;
    }

    @GetMapping("/path/{path}")
    public String sayHelloWithPath(@PathVariable String path,
                                  @RequestParam(value = "name", defaultValue = "안 들어옴") String name) {
        return "Hello, Spring Boot! " + path + " " + name;
    }

    @GetMapping("/shoe/{path}")
    public String sayHelloWithFootSize(@PathVariable String path,
                                       @RequestParam(value = "name", defaultValue = "안 들어옴") String name,
                                       @RequestParam(value = "footSize", defaultValue = "안 들어옴2") String foot) {
        return "Hello, Spring Boot! " + path + " " + name + " 신발사이즈는 " + foot;
    }
}
