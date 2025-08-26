package com.example.unisportserver.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Normalizer {
    public String Normalize(String string){
        return string == null ? null : string.trim().toLowerCase();
    }
}
