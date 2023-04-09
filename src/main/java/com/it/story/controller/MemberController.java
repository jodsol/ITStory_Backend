package com.it.story.controller;

import com.it.story.config.exception.MemberNotFoundException;
import com.it.story.dto.member.ProfileRes;
import com.it.story.entity.Member;
import com.it.story.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
@Slf4j
@Tag(name = "Member", description = "Member Controller Api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ProfileRes profile(@AuthenticationPrincipal UserDetails userDetails) throws MemberNotFoundException {
        System.out.println("userDetails = " + userDetails);
        Member memberDetail = memberService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new MemberNotFoundException());

        return ProfileRes.builder()
                .email(memberDetail.getEmail())
                .name(memberDetail.getName())
                .build();
    }

    @GetMapping("/profile/view/{name}")
    public ProfileRes memberProfile(@PathVariable String name) throws MemberNotFoundException {
        Member user = memberService.findByName(name)
                .orElseThrow(MemberNotFoundException::new);

        return ProfileRes.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @GetMapping("/memberList")
    public List<Member> showMemberList() {
        return memberService.findAll();
    }
}
