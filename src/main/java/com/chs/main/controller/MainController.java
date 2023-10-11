package com.chs.main.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping("/")
    public String index(HttpSession session, Model model) {

        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        Authentication authentication2 = null;

        Object securityContextObj = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContextObj instanceof SecurityContext) {
            SecurityContext securityContext = (SecurityContext) securityContextObj;
            authentication2 = securityContext.getAuthentication();
        }

        System.out.println("authentication1 = " + authentication1);
        System.out.println("authentication2 = " + authentication2);

        System.out.println("authentication1 hashCode = " + authentication1.hashCode());
//        System.out.println("authentication2 hashCode = " + authentication2.hashCode());

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean isLoggedIn = authentication != null && authentication.isAuthenticated();
//
//        boolean loggedInOwner = authentication.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"));
//        boolean loggedInUser = authentication.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
//
//        model.addAttribute("loggedInOwner", loggedInOwner);
//        model.addAttribute("loggedInUser", loggedInUser);

        return "index";
    }
}
