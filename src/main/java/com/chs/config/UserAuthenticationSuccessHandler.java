package com.chs.config;

import com.chs.member.owner.service.OwnerService;
import com.chs.member.user.service.UserService;
import com.chs.security.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final OwnerService ownerService;
    @Autowired
    TokenProvider tokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        // Get the authentication object from SecurityContextHolder
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Log the authentication object (you can log any specific information you need)
        logger.debug("Authentication object: {}", auth);
        System.out.printf("Authentication object: {%s} \n"  , auth);

        // SpringSecurity 인증 후 로그인 객체를 가져오기 위해 작성
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        String userId = userDetails.getUsername();
        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();

        boolean isOwner = ownerService.loadUserByUsername(userId)
                .getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_OWNER"));

        // 회원 별 최종 로그인 날짜 업데이트 작업
        if(isOwner) {
            ownerService.updateLastLoginDt(userId, LocalDateTime.now());
        } else {
            userService.updateLastLoginDt(userId, LocalDateTime.now());
        }

        String token = tokenProvider.generateAuthToken(authentication.getName());

        response.addHeader("Authorization", "Bearer " + token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
    }


}
