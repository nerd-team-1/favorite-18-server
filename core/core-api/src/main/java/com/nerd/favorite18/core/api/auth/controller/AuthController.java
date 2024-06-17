package com.nerd.favorite18.core.api.auth.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api.auth.business.AuthBusiness;
import com.nerd.favorite18.core.api.auth.dto.AuthRefreshRequest;
import com.nerd.favorite18.core.api.jwt.dto.JwtRefreshResponse;
import com.nerd.favorite18.core.api.support.response.ApiResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthBusiness authBusiness;

    @PostMapping("/refresh-token")
    public ApiResponse<JwtRefreshResponse> refreshToken(@UserSession UserDto user, @RequestBody AuthRefreshRequest request) {
        final JwtRefreshResponse jwtRefreshResponse = authBusiness.refreshToken(request);

        return ApiResponse.success(jwtRefreshResponse);
    }
}
