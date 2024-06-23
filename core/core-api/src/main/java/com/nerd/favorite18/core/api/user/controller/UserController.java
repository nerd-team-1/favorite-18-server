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

    // 로그인 사용자 정보 확인
    @GetMapping("/me")
    public ApiResponse<UserDto> me(@UserSession UserDto user) {

        return ApiResponse.success(user);
    }

    // 사용자 닉네임 변경
    @PutMapping("/nickname")
    public ApiResponse<Void> updateNickname(@UserSession UserDto user, @RequestBody UserUpdateNicknameRequest request) {
        userBusiness.updateNickname(user, request);

        return ApiResponse.success();
    }

    // 회원 탈퇴
    @DeleteMapping
    public ApiResponse<Void> deleteUser(@UserSession UserDto user) {
        userBusiness.deleteUser(user);

        return ApiResponse.success();
    }
}
