package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.LoginDto;
import com.example.unisportserver.data.dto.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public AuthService() {}

    public LoginDto login(LoginDto loginDto){
        return loginDto;
    }

    public RegisterDto register(RegisterDto registerDto){
        return registerDto;
    }
}
