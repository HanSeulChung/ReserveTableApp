package com.chs.member.controller;

import com.chs.member.owner.service.OwnerService;
import com.chs.member.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthLoginController {
    private final OwnerService ownerService;
    private final UserService userService;

//    @GetMapping("auth/signin")
//    public String loginGet() {
//        return "member/login";
//    }
//
//
//    @PostMapping("auth/signin")
//    public String login(Model model, Principal principal) {
//
//        if (principal != null) {
//            // Get the logged-in user's username
//            String username = principal.getName();
//            // Add the username to the Thymeleaf model
//            model.addAttribute("username", username);
//        }
//
//
//
//        return "index";
//    }


    @RequestMapping("auth/signin")
    public String login(Model model) {

        return "member/login";
    }
//    @GetMapping("/auth/signinSuccess")
//    public String loginSuccess(Model model, Principal principal) {
//
//        if(principal != null) {
//            // Get the logged-in user's username
//            String username = principal.getName();
//            // Add the username to the Thymeleaf model
//            model.addAttribute("username", username);
//        }
//
//        return "member/login_success";
//    }

    @RequestMapping("/auth/logout")
    public String logout() {
        return "member/logout";
    }
}
