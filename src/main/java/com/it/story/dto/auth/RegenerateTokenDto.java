package com.it.story.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegenerateTokenDto {
    private String refresh_token;
}
