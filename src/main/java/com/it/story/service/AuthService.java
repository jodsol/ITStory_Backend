package com.it.story.service;

import com.it.story.dto.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    /**
     * 유저의 정보로 회원가입
     * @param signUpReq 가입할 유저의 정보 Dto
     * @return 가입된 유저 정보
     */
    SignUpRes signUp(SignUpReq signUpReq);

    /**
     * 유저 정보로 로그인
     * @param signInReq 유저의 이메일과 비밀번호
     * @return json web token
     */
    ResponseEntity<TokenDto> signIn(SignInReq signInReq);

    ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto refreshTokenDto);
}
