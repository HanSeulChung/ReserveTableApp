package com.chs.member.controller;

import com.chs.member.owner.service.OwnerService;
import com.chs.member.user.service.UserService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthLoginController {
    private final OwnerService ownerService;
    private final UserService userService;

    @Autowired
    private TokenAuthenticationProvider tokenProvider;

    @GetMapping("auth/signin")
    public String login() {

        return "member/login";
    }

    @RequestMapping(value = "/auth/signin", method = RequestMethod.POST)
    public String handleLogin(HttpServletRequest request, Model model) {
//        if (request != null) {
//
//        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String token = tokenProvider.generateAuthToken(username);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);


        UserDetails userDetails = userService.loadUserByUsername(username);
        // 로그인 성공 시 Authentication 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        // SecurityContextHolder에 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("Generated Token: " + token);

        model.addAttribute("username", username);
        return "member/login_success";
    }

//    @PostMapping("/auth/signin")
//    public ResponseEntity<?> handleLogin(HttpServletRequest request) {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        String token = tokenProvider.generateAuthToken(username);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + token);
//
//
//        UserDetails userDetails = userService.loadUserByUsername(username);
//        // 로그인 성공 시 Authentication 객체 생성
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//
//        // SecurityContextHolder에 설정
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        System.out.println("Generated Token: " + token);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body("Login successful.");
//
//    }


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
