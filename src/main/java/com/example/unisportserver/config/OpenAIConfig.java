package com.example.unisportserver.config;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    @Bean
    public OpenAIClient openAIClient(@Value("${openai.api-key}") String apikey) {
        // OPENAI_API_KEY 등 환경변수에서 읽음
        return OpenAIOkHttpClient.builder()
                .apiKey(apikey)
                .build(); // 공식 SDK 권장 방식
    }
}