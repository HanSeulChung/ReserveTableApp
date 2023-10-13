package com.chs.member.user.controller;

import com.chs.member.model.Auth;
import com.chs.member.user.service.UserService;
import com.chs.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 일반 회원(User) 로그인 및 회원가입
 */
@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenAuthenticationProvider tokenProvider;

    @GetMapping("/signup")
    public String signup() {
        return "member/register";
    }

    @PostMapping("/signup")
    public String signupSummit(Model model, HttpServletRequest request
            , @Valid Auth.SignUp parameter) {
        // 회원 가입 API
        var result = this.userService.register(parameter);
        log.info(String.valueOf(result));
        model.addAttribute("result", result);
        return "member/register_complete";
    }

//    @RequestMapping("/signin")
//    public String login() {
//
//        return "member/user_login";
//    }

//    @PostMapping("/signin")
//    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
//        // 로그인 APIauthenticate
//        var user = this.userService.(request);
//        var token = this.tokenProvider.generateUserToken(user.getUserId());
//        log.info("user login -> " + request.getUserId());
//        return ResponseEntity.ok(token);
//    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam String userId,
                                  @RequestBody @Valid Auth.SignEdit request) {
        var result = userService.updateMember(userId, request);
        return ResponseEntity.ok(result);
    }
}
