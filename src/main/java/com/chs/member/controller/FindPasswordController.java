package com.chs.member.controller;

import com.chs.member.model.ResetPasswordInput;
import com.chs.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class FindPasswordController {
    private final MemberService memberService;

    @GetMapping("/find-password")
    public String findPassword() {

        return "member/find_password";
    }

    @PostMapping("/find-password")
    public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {

        boolean result = memberService.sendResetPassword(parameter);
        model.addAttribute("result", result);

        return "member/find_password_result";
    }
}
