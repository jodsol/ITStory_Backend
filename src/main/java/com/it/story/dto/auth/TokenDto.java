package com.it.story.dto.auth;

import lombok.*;

@AllArgsConstructor
@Getter
public class TokenDto {
//    private String grantType;
    private String accessToken;
    private String refreshToken;
}
