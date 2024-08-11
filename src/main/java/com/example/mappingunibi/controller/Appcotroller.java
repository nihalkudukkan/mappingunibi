package com.example.mappingunibi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Appcotroller {

    @GetMapping("/")
    public String get() {
        return "<h1>Hello world</h1>";
    }

}
