package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.LoginDto;
import com.example.unisportserver.data.dto.RegisterDto;
import com.example.unisportserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    public LoginDto login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }

    @PostMapping(value = "/register")
    public RegisterDto register(@RequestBody RegisterDto registerDto){
        return authService.register(registerDto);
    }
}
