package com.example.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    /*
        초기 화면
     */
    @GetMapping("/")
    public ResponseEntity index() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
