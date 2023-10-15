package com.chs.member.controller;

import com.chs.member.model.Auth;
import com.chs.member.service.MemberService;
import com.chs.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 로그인(User, Owner)
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthLoginController {
    private final MemberService memberService;

    @Autowired
    private TokenAuthenticationProvider tokenProvider;

    @GetMapping("auth/signin")
    public String login() {

        return "member/login";
    }

    @PostMapping(value = "/auth/signin")
    public String handleLogin(HttpServletRequest httpRequest, Model model) {

        String username = httpRequest.getParameter("username");
        String token = tokenProvider.generateAuthToken(username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        UserDetails userDetails = memberService.loadUserByUsername(username);

//         로그인 성공 시 Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        model.addAttribute("username", username);

        return "member/login_success";
    }

    @GetMapping("/auth/signinSuccess")
    public String loginSuccess() {
        return "member/login_success";
    }

    @RequestMapping("/auth/logout")
    public String logout() {
        return "member/logout";
    }

    @RequestMapping("/auth/logout_success")
    public String logoutSuccess() {
        return "member/logout_success";
    }

}
