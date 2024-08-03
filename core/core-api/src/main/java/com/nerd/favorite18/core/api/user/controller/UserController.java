package com.nerd.favorite18.core.api.user.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.user.business.UserBusiness;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserUpdateNicknameRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "1.3 [사용자]", description = "사용자 정보 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {
    private final UserBusiness userBusiness;

    // 로그인 사용자 정보 확인
    @Operation(summary = "내 정보 조회", description = "로그인 사용자 정보를 조회한다.")
    @GetMapping("/me")
    public ApiResponse<UserDto> me(@UserSession UserDto user) {

        return ApiResponse.success(user);
    }

    // 사용자 닉네임 변경
    @Operation(summary = "닉네임 변경", description = "사용자 닉네임을 변경한다.")
    @PutMapping("/nickname")
    public ApiResponse<UserDto> updateNickname(@UserSession UserDto user, @RequestBody UserUpdateNicknameRequest request) {
        final UserDto userDto = userBusiness.updateNickname(user, request);

        return ApiResponse.success(userDto);
    }

    // 회원 탈퇴
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 요청한다.")
    @DeleteMapping
    public ApiResponse<Void> deleteUser(@UserSession UserDto user) {
        userBusiness.deleteUser(user);

        return ApiResponse.success();
    }
}
