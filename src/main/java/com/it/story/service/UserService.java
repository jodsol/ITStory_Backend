package com.it.story.service;

import com.it.story.dto.UserDto;
import com.it.story.dto.UserFormDto;

public interface UserService {
    UserDto createUser(UserFormDto userFormDto);
}
