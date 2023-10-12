package com.chs.member.controller;

import com.chs.member.owner.service.OwnerService;
import com.chs.member.user.service.UserService;
import com.chs.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;


@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthLoginController {
    private final OwnerService ownerService;
    private final UserService userService;

    @Autowired
    TokenProvider tokenProvider;

    @GetMapping("auth/signin")
    public String login() {

        return "member/login";
    }

    @PostMapping("/auth/signin")
    public String handleLogin(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String token = tokenProvider.generateAuthToken(username);
        System.out.println("Generated Token: " + token);

        return "member/login";

    }


    @GetMapping("/auth/signinSuccess")
    public String loginSuccess() {
        return "member/login_success";
    }

    @RequestMapping("/auth/logout")
    public String logout() {
        return "member/logout";
    }
}
