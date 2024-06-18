package com.nerd.favorite18.core.api.user.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.user.business.UserBusiness;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserUpdateNicknameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {
    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public ApiResponse<UserDto> me(@UserSession UserDto user) {
        return ApiResponse.success(user);
    }

    @PutMapping("/set-nickname")
    public ApiResponse<Void> updateNickname(@UserSession UserDto user, @RequestBody UserUpdateNicknameRequest request) {
        userBusiness.updateNickname(user, request);

        return ApiResponse.success();
    }
}
