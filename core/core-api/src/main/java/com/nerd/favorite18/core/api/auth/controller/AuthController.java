package com.nerd.favorite18.core.api.auth.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.auth.business.AuthBusiness;
import com.nerd.favorite18.core.api.auth.dto.request.AuthRefreshRequest;
import com.nerd.favorite18.core.api.jwt.dto.JwtResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "1.2 [토큰 재발급 및 로그아웃]", description = "사용자 토큰 재발급 및 로그아웃")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthBusiness authBusiness;

    @Operation(summary = "토큰 재발급", description = "토큰 재발급을 요청하며, 성공시 새로운 토큰을 발급받는다.")
    @PostMapping("/refresh-token")
    public ApiResponse<JwtResponse> refreshToken(@RequestBody AuthRefreshRequest request) {
        final JwtResponse jwtResponse = authBusiness.refreshToken(request);

        return ApiResponse.success(jwtResponse);
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 요청하며, 성공시 세션에서 사용자 정보를 제거한다.")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@UserSession UserDto user) {
        authBusiness.logout(user);

        return ApiResponse.success();
    }
}
