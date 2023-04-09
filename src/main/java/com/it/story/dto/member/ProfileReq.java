package com.it.story.dto.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileReq {
    private String name;
}
