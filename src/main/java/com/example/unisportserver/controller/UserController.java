package com.example.unisportserver.controller;

import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.data.repository.UserRepository;
import com.example.unisportserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

//    @PostMapping
//    public UserDto createUser(@RequestBody UserDto request) {
//        return userService.createUser(request);
//    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping()
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
