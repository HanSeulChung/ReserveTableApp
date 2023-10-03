package com.chs.member.owner.controller;

import com.chs.member.owner.service.OwnerService;
import com.chs.member.model.Auth;
import com.chs.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 점주 회원(Owner) 로그인 및 회원가입
 */
@Slf4j
@RestController
@RequestMapping("/auth/owner")
@RequiredArgsConstructor
public class AuthOwnerController {


    private final OwnerService ownerService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid Auth.SignUp request) {
        // 회원 가입 API
        var result = this.ownerService.register(request);
        log.info(String.valueOf(result));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        // 로그인 API
        var member = this.ownerService.authenticate(request);
        var token = this.tokenProvider.generateOwnerToken(member.getUserId());
        log.info("user login -> " + request.getUserId());
        return ResponseEntity.ok(token);
    }
}
