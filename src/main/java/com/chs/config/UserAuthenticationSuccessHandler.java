package com.chs.config;

import com.chs.member.owner.service.OwnerService;
import com.chs.member.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
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

        boolean isOwner = ownerService.loadUserByUsername(userId)
                .getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_OWNER"));

        // 회원 별 최종 로그인 날짜 업데이트 작업
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(isOwner) {
            ownerService.updateLastLoginDt(userId, LocalDateTime.now());
            authorities.add(new SimpleGrantedAuthority("ROLE_OWNER"));
        } else {
            userService.updateLastLoginDt(userId, LocalDateTime.now());
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        }


        super.onAuthenticationSuccess(request, response, authentication);

        if (authentication != null && authentication.isAuthenticated()) {
            // Modify the authentication object or extract information from it
            String userName = "John Doe";  // You can get the username from your user object
            Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(), authentication.getCredentials(), authorities);
            SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);
        }
    }
}
