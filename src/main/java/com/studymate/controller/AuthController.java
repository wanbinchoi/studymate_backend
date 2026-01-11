package com.studymate.controller;


import com.studymate.dto.auth.LoginRequest;
import com.studymate.dto.auth.SignupRequest;
import com.studymate.dto.auth.SignupResponse;
import com.studymate.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    /**
     *  회원가입
     *  POST /api/auth/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request){
        SignupResponse response = userService.signup(request);
        return ResponseEntity.status(201).body(response);
    }

    /**
     *  로그인
     *  POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<SignupResponse> login(@Valid @RequestBody LoginRequest request){
        SignupResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
