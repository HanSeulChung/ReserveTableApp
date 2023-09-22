package com.chs.member.controller;

import com.chs.member.Auth;
import com.chs.member.service.TokenProvider;
import com.chs.member.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일반 회원(User) 로그인 및 회원가입
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
        // 회원 가입 API
        var result = this.userService.register(request);
        log.info(String.valueOf(result));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        // 로그인 API
        var user = this.userService.authenticate(request);
        var token = this.tokenProvider.generateUserToken(user.getUserId());
        log.info("user login -> " + request.getUserId());
        return ResponseEntity.ok(token);
    }
}
