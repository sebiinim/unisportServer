package com.example.unisportserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootHealthController {

    @GetMapping("/")   // 루트 경로에 매핑
    public String health() {
        return "OK";   // 그냥 단순 문자열 리턴
    }
}
