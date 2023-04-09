package com.it.story.service.impl;

import com.it.story.config.handler.JwtProvider;
import com.it.story.entity.Member;
import com.it.story.repository.MemberRepository;
import com.it.story.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    @Override
    public Member updateUser(Member user, String newInfo) {
        return null;
    }
}
