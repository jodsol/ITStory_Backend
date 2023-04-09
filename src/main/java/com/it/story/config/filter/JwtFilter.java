package com.it.story.config.filter;

import com.it.story.config.exception.CustomException;
import com.it.story.config.handler.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        try {
            if (token != null && jwtProvider.validateAccessToken(token)) {
                Authentication auth = jwtProvider.getAuthenticationByAccessToken(token);
                SecurityContextHolder.getContext().setAuthentication(auth); // 정상 토큰이면 SecurityContext에 저장
            }
        } catch (CustomException e) {
            SecurityContextHolder.clearContext();
            response.sendError(e.getHttpStatus().value(), e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}

