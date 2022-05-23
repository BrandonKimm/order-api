package com.brandon.controller;

import com.brandon.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/hello-brandon")
    public String helloBrandon(){
        System.out.println("hello-brandon");
        return "hello-brandon";
    }
}
