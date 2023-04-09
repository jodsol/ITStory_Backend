package com.it.story.controller;

import com.it.story.dto.auth.*;
import com.it.story.service.AuthService;
import com.it.story.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
@Tag(name = "Auth", description = "Auth Controller Api")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signUp")
    public SignUpRes signUp(@Validated SignUpReq signUpReq) {
        return authService.signUp(signUpReq);
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenDto> signIn(@Validated SignInReq signInReq) {
        return authService.signIn(signInReq);
    }

    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@Validated RegenerateTokenDto refreshTokenDto) {
        return authService.regenerateToken(refreshTokenDto);
    }

}
