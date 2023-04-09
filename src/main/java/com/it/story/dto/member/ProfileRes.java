package com.it.story.dto.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileRes {
    private String email;
    private String name;
}
