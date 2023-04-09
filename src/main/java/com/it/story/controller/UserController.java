package com.it.story.controller;

import com.it.story.dto.UserDto;
import com.it.story.entity.User;
import com.it.story.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "User", description = "User Controller Api")
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    @Operation(summary = "유저 회원가입", description = "유저 회원가입", tags = {"View"})
    public String signup() {
        return null;
    }

}
