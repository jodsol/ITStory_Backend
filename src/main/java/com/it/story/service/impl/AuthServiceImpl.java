package com.it.story.service.impl;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.it.story.config.exception.CustomException;
import com.it.story.config.handler.JwtProvider;
import com.it.story.dto.auth.*;
import com.it.story.entity.Member;
import com.it.story.repository.MemberRepository;
import com.it.story.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.token.refresh-token-expire-length}")
    private long refresh_token_expire_time;

    @Override
    @Transactional
    public SignUpRes signUp(SignUpReq signUpReq){
        System.out.println("signUpReq = " + signUpReq.toString());

        if(memberRepository.existsByEmail(signUpReq.getEmail())) {
            return new SignUpRes(false, "Your Mail already Exist.");
        }
        Member newMember = signUpReq.toMemberEntity();
        newMember.hashPassword(bCryptPasswordEncoder);

        Member user = memberRepository.save(newMember);
        if(!Objects.isNull(user)) {
            return new SignUpRes(true, null);
        }
        return new SignUpRes(false, "Fail to Sign Up");
    }

    @Override
    public ResponseEntity<TokenDto> signIn(SignInReq signInReq) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInReq.getEmail(),
                            signInReq.getPassword()
                    )
            );

            String refresh_token = jwtProvider.generateRefreshToken(authentication);

            TokenDto tokenDto = new TokenDto(
                    jwtProvider.generateAccessToken(authentication),
                    refresh_token
            );

            // Redis에 저장 - 만료 시간 설정을 통해 자동 삭제 처리
            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    refresh_token,
                    refresh_token_expire_time,
                    TimeUnit.MILLISECONDS
            );

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + tokenDto.getAccessToken());

            return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid credentials supplied", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto refreshTokenDto) {
        String refresh_token = refreshTokenDto.getRefresh_token();
        try {
            // Refresh Token 검증
            if (!jwtProvider.validateRefreshToken(refresh_token)) {
                throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
            }

            // Access Token 에서 User email를 가져온다.
            Authentication authentication = jwtProvider.getAuthenticationByRefreshToken(refresh_token);

            // Redis에서 저장된 Refresh Token 값을 가져온다.
            String refreshToken = redisTemplate.opsForValue().get(authentication.getName());
            if(!refreshToken.equals(refresh_token)) {
                throw new CustomException("Refresh Token doesn't match.", HttpStatus.BAD_REQUEST);
            }

            // 토큰 재발행
            String new_refresh_token = jwtProvider.generateRefreshToken(authentication);
            TokenDto tokenDto = new TokenDto(
                    jwtProvider.generateAccessToken(authentication),
                    new_refresh_token
            );

            // RefreshToken Redis에 업데이트
            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    new_refresh_token,
                    refresh_token_expire_time,
                    TimeUnit.MILLISECONDS
            );

            HttpHeaders httpHeaders = new HttpHeaders();

            return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
        }
    }
}
