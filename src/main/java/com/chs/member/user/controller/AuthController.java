package com.chs.member.user.controller;

import com.chs.member.model.Auth;
import com.chs.security.TokenProvider;
import com.chs.member.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<?> signup(@RequestBody @Valid Auth.SignUp request) {
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

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam String userId,
                                  @RequestBody @Valid Auth.SignEdit request) {
        var result = userService.updateMember(userId, request);
        return ResponseEntity.ok(result);
    }
}
