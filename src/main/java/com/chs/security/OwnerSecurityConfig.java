//package com.chs.security;
//
//import com.chs.config.*;
//import com.chs.member.owner.service.OwnerService;
//import com.chs.member.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Slf4j
//@Configuration
//@EnableWebSecurity
//@Order(1)
//@RequiredArgsConstructor
//public class OwnerSecurityConfig extends WebSecurityConfigurerAdapter {
//    private final JwtAuthenticationFilter authenticationFilter;
//
//    private final OwnerService ownerService;
//
//    private final AppConfig appConfig;
//
//    @Bean
//    OwnerAuthenticationFailureHandler getOwnerFailureHandler() {
//
//        return new OwnerAuthenticationFailureHandler();
//    }
//    @Bean
//    OwnerAuthenticationSuccessHandler getOwnerSuccessHandler() {
//
//        return new OwnerAuthenticationSuccessHandler(ownerService);
//    }
//
//    private static final String[] AUTH_WHITELIST = {
//            "/swagger-resources/**",
//            "/swagger-ui.html",
//            "/v2/api-docs",
//            "/webjars/**",
//            "/h2-console/**"
//    };
//
//    private static final String[] AUTH_OWNERLIST = {
//            "/auth/owner/register/store",
//            "/auth/owner/delete/store",
//            "/auth/owner/update/store",
//            "/auth/owner/read/store",
//            "/auth/owner/reservation/**"
//    };
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(AUTH_WHITELIST);
//    }
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable();
//        http.headers().frameOptions().sameOrigin();
//
//        http
//                .authorizeRequests()
//                .antMatchers(AUTH_OWNERLIST).hasAuthority("ROLE_OWNER")
//                .antMatchers("/**/signup", "/**/signin","/store/all", "/store/search/**").permitAll();
//
//        http.formLogin()
//                .loginPage("/auth/owner/signin")
//                .defaultSuccessUrl("/")
//                .failureHandler(getOwnerFailureHandler())
//                .successHandler(getOwnerSuccessHandler())
//                .permitAll();
//
//
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http
//                .addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//        http.exceptionHandling()
//                .accessDeniedPage("/error/denied");
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(ownerService)
//                .passwordEncoder(appConfig.passwordEncoder());
//
//        super.configure(auth);
//    }
//}
