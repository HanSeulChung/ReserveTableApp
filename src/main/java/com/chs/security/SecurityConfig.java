package com.chs.security;

import com.chs.config.OwnerAuthenticationFailureHandler;
import com.chs.config.OwnerAuthenticationSuccessHandler;
import com.chs.config.UserAuthenticationFailureHandler;
import com.chs.config.UserAuthenticationSuccessHandler;
import com.chs.member.owner.service.OwnerService;
import com.chs.member.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationFilter authenticationFilter;

    private final UserService userService;
    private final OwnerService ownerService;

    @Bean
    UserAuthenticationFailureHandler getUserFailureHandler() {

        return new UserAuthenticationFailureHandler();
    }
    @Bean
    UserAuthenticationSuccessHandler getUserSuccessHandler() {

        return new UserAuthenticationSuccessHandler(userService);
    }

    @Bean
    OwnerAuthenticationFailureHandler getOwnerFailureHandler() {

        return new OwnerAuthenticationFailureHandler();
    }
    @Bean
    OwnerAuthenticationSuccessHandler getOwnerSuccessHandler() {

        return new OwnerAuthenticationSuccessHandler(ownerService);
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/h2-console/**"
    };

    private static final String[] AUTH_OWNERLIST = {
            "/auth/owner/register/store",
            "/auth/owner/delete/store",
            "/auth/owner/update/store",
            "/auth/owner/read/store",
            "/auth/owner/reservation/**"
    };

    private static final String[] AUTH_USERLIST = {
            "/store/reserve",
            "/myreservation/**"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(AUTH_OWNERLIST).hasAuthority("ROLE_OWNER")
                .antMatchers(AUTH_USERLIST).hasAuthority("ROLE_USER")
                .antMatchers("/**/signup", "/**/signin","/store/all", "/store/search/**").permitAll();


        http.formLogin()
                .loginPage("/auth/signin")
                .failureHandler(getUserFailureHandler())
                .successHandler(getUserSuccessHandler())
                .permitAll();

        http.formLogin()
                .loginPage("/auth/owner/signin")
                .failureHandler(getOwnerFailureHandler())
                .successHandler(getOwnerSuccessHandler())
                .permitAll();


        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);


        http.exceptionHandling()
                .accessDeniedPage("/error/denied");
    }
}
