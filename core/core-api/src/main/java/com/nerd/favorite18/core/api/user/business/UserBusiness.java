package com.nerd.favorite18.core.api.user.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserUpdateNicknameRequest;
import com.nerd.favorite18.core.api.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Business
public class UserBusiness {
    private final UserService userService;

    public void updateNickname(UserDto user, UserUpdateNicknameRequest request) {
        userService.updateNickname(user, request);
    }
}
