package com.example.appjwtrealemailauditing.controller;

import com.example.appjwtrealemailauditing.dto.ApiResponse;
import com.example.appjwtrealemailauditing.dto.LoginDto;
import com.example.appjwtrealemailauditing.dto.RegisterDto;
import com.example.appjwtrealemailauditing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService authService;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDto) {
        ApiResponse response = authService.register(registerDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email){
        ApiResponse response = authService.verifyEmail(emailCode, email);
        return ResponseEntity.status(response.isSuccess()?200:409).body(response);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse response = authService.login(loginDto);
        return ResponseEntity.status(response.isSuccess()?200:401 ).body(response);
    }

}
