package com.nerd.favorite18.core.api.auth.controller;

import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.auth.business.AuthBusiness;
import com.nerd.favorite18.core.api.auth.dto.request.AuthLoginGoogleRequest;
import com.nerd.favorite18.core.api.auth.dto.request.AuthSignupGoogleRequest;
import com.nerd.favorite18.core.api.jwt.dto.JwtResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/open-api/auth")
@RestController
public class AuthOpenApiController {
    private final AuthBusiness authBusiness;

    // 모바일 회원가입 진행
    @PostMapping("/signup")
    public ApiResponse<UserDto> signUp(@RequestBody AuthSignupGoogleRequest request) {
        final UserDto userDto = authBusiness.signUp(request);

        return ApiResponse.success(userDto);
    }

    // 모바일 로그인 진행
    @PostMapping("/login/google")
    public ApiResponse<JwtResponse> loginGoogle(@RequestBody AuthLoginGoogleRequest request) {
        final JwtResponse jwtResponse = authBusiness.loginGoogle(request);

        return ApiResponse.success(jwtResponse);
    }

    // 로그인 url 발행 요청
    @GetMapping("/login")
    public URI login() {

        return authBusiness.login();
    }

    // 웹 로그인 콜백 주소
    @GetMapping("/login/oauth2/code/google")
    public ApiResponse<JwtResponse> callbackGoogle(@RequestParam("code") String authorizationCode) {
        final JwtResponse jwtResponse = authBusiness.callbackGoogle(authorizationCode);

        return ApiResponse.success(jwtResponse);
    }
}
