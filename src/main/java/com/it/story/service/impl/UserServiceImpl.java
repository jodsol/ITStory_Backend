package com.it.story.service.impl;

import com.it.story.domain.Role;
import com.it.story.dto.UserDto;
import com.it.story.dto.UserFormDto;
import com.it.story.entity.User;
import com.it.story.repository.UserRepository;
import com.it.story.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserFormDto userFormDto) {
        // 이메일 중복 확인
        if(userRepository.findByEmail(userFormDto.getEmail()) != null) {
            return null;
        }
        return null;

        // 가입한 성공한 모든 유저는 "USER" 권한 부여
//        User user = userRepository.save(User.builder()
//                .password(bCryptPasswordEncoder.encode(userFormDto.getPassword()))
//                .email(userFormDto.getEmail())
//                .role(Role.USER)
//                .build());
//
//        return UserDto.builder()
//                .id(user.getId())
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .role(user.getRole())
//                .build();
    }
}
