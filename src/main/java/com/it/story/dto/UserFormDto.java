package com.it.story.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserFormDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 4, max = 100, message = "비밀번호는 4글자 이상, 100자 이하로 입력해주세요.")
    private String password;
}
