package com.studymate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    /**
     * 인증이 필요한 테스트 API
     * GET /api/test/auth
     */
    @GetMapping("/auth")
    public ResponseEntity<Map<String, String>> testAuth(
            @AuthenticationPrincipal String userId
    ){
        Map<String, String> response = new HashMap<>();
        response.put("message", "인증 성공!");
        response.put("userId", userId);

        return ResponseEntity.ok(response);
    }

    /**
     * 인증이 필요 없는 테스트 API (비교용)
     * GET api/test/public
     */
    @GetMapping("/public")
    public ResponseEntity<Map<String, String>> testPublic(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "누구나 접근 가능합니다.");

        return ResponseEntity.ok(response);
    }
}
