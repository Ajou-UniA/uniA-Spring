package com.example.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    /*
        초기 화면
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
