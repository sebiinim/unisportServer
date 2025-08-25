package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.ChatGptRequest;
import com.example.unisportserver.service.ChatGptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chatgpt")
public class ChatGptController {

    private final ChatGptService chatGptService;

    @PostMapping
    public String chat(@Valid @RequestBody ChatGptRequest chatGptRequest) {
        return chatGptService.ask(chatGptRequest.message());
    }
}
