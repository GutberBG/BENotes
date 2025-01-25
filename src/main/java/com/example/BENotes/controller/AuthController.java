package com.example.BENotes.controller;

import com.example.BENotes.dto.UserDTO;
import com.example.BENotes.dto.UserMapper;
import com.example.BENotes.entity.User;
import com.example.BENotes.service.AuthService;
import com.example.BENotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return authService.login(username, password);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = UserMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }
}
