package com.chs.member.owner.controller;

import com.chs.member.model.Auth;
import com.chs.member.owner.service.OwnerService;
import com.chs.security.TokenAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 점주 회원(Owner) 회원가입
 */
@Slf4j
@Controller
@RequestMapping("/auth/owner")
@RequiredArgsConstructor
public class AuthOwnerController {


    private final OwnerService ownerService;
    private final TokenAuthenticationProvider tokenProvider;

    @GetMapping("/signup")
    public String signup() {
        return "member/register";
    }

    @PostMapping("/signup")
    public String signupSummit(Model model, HttpServletRequest request
            ,@Valid Auth.SignUp parameter) {
        // 회원 가입 API
        var result = this.ownerService.register(parameter);
        log.info(String.valueOf(result));
        model.addAttribute("result", result);
        return "member/register_complete";
    }
}
