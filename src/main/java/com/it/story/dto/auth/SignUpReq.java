package com.it.story.dto.auth;

import com.it.story.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpReq {

    @NotEmpty(message = "Please enter your Email")
    @Email
    private String email;
    @NotEmpty(message = "Please enter your Password")
    private String password;
    @NotEmpty(message = "Please enter your Name")
    private String name;

    @Builder
    public SignUpReq(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    /**
     * Transform to User Entity
     * @return User Entity
     */
    public Member toMemberEntity() {
        return Member.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .name(this.getName())
                .build();
    }
}
