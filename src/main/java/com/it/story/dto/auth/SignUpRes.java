package com.it.story.dto.auth;

import com.it.story.dto.common.CoreRes;
import lombok.Getter;

@Getter
public class SignUpRes extends CoreRes {
    public SignUpRes(boolean ok, String error) {
        super(ok, error);
    }
}
