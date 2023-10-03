package com.chs.config;

import com.chs.member.owner.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class OwnerAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OwnerService ownerService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // SpringSecurity 인증 후 로그인 객체를 가져오기 위해 작성
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;

        String userId = userDetails.getUsername();
        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();

        // 회원 별 최종 로그인 날짜 업데이트 작업
        ownerService.updateLastLoginDt(userId, LocalDateTime.now());

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
