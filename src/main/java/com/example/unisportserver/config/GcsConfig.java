// src/main/java/com/example/unisportserver/config/GcsConfig.java
package com.example.unisportserver.config;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GcsConfig {
    @Bean
    public Storage storage() {
        // GOOGLE_APPLICATION_CREDENTIALS를 자동 인식
        return StorageOptions.getDefaultInstance().getService();
    }
}

