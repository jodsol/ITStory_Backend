package com.it.story.config;

import com.it.story.config.filter.JwtFilter;
import com.it.story.config.handler.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfigurerAdapter를 확장.
 * JwtTokenProvider를 주입받음.
 * JwtFilter를 통해 Security filterchain에 filter를 추가 등록
 */
@RequiredArgsConstructor
public class JwtSecurityConfig  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtProvider jwtProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtFilter customFilter = new JwtFilter(jwtProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
