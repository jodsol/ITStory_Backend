package com.it.story.service.impl;

import com.it.story.entity.Member;
import com.it.story.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberDetailServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new UsernameNotFoundException(memberEmail + " 사용자를 찾을 수 없습니다."));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }
}
