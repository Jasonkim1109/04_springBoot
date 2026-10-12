package com.example.wonit.controller;

import org.springframework.web.bind.annotation.*;

// 라우팅 기능: 사용자의 요청(Request)을 주소줄과 매핑해서 서버에 전달하는 역할
@RestController
@RequestMapping("/hello")  // localhost:8080/hello
public class HelloController {

//    @GetMapping
//    public String sayHello() {
//        return "Hello, Spring Boot!";
//    }

//    @GetMapping("/{path}")  // localhost:8080/hello/경로변수(PathVarible)
//    public String sayHello(@PathVariable String path) {
//        return "Hello, Spring Boot!" + path;
//    }

    @GetMapping // localhost:8080/hello?name=값
    public String sayHello(@RequestParam(value="name") String name) {
        return "Hello, Spring Boot!" + name;
    }
}
