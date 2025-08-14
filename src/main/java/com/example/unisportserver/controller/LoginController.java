package com.example.unisportserver.controller;

import com.example.unisportserver.dto.LoginDTO;
import com.example.unisportserver.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public String login(@RequestBody LoginDTO loginDTO) {
        return loginDTO.toString();
    }
}
