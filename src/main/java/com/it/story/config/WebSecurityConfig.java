package com.it.story.config;

import com.it.story.config.handler.JwtAccessDeniedHandler;
import com.it.story.config.handler.JwtAuthenticationEntryPoint;
import com.it.story.config.handler.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 환경 설정을 구성하기 위한 클래스입니다.
 * 웹 서비스가 로드 될때 Spring Container 의해 관리가 되는 클래스이며 사용자에 대한 ‘인증’과 ‘인가’에 대한 구성을 Bean 메서드로 주입을 한다.
 */
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    // 추가된 jwt 관련 친구들을 security config에 추가
    private final JwtProvider jwtProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable csrf to use token
        http
                .csrf().disable();

        //
        http
                .authorizeHttpRequests()
                .requestMatchers(
                        "/",
                        "/auth/signUp",
                        "/user/userList",
                        "/auth/signIn*",
                        "/user/profile/view/**",
                        "/auth/regenerateToken",
                        "/favicon.ico"
                ).permitAll()
                .anyRequest().authenticated();

        // No session will be created or used by spring security
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // exception handling for jwt
        http
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        // Apply JWT
        http.apply(new JwtSecurityConfig(jwtProvider));

        return http.build();
    }
}
