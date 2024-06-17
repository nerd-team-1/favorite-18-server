package com.nerd.favorite18.core.api.auth.controller;

import com.nerd.favorite18.core.api.auth.business.AuthBusiness;
import com.nerd.favorite18.core.api.jwt.dto.JwtResponse;
import com.nerd.favorite18.core.api.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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



    @GetMapping("/login")
    public URI login() {

        return authBusiness.login();
    }

    @GetMapping("/login/oauth2/code/google")
    public ApiResponse<JwtResponse> callbackGoogle(@RequestParam("code") String authorizationCode) {
        final JwtResponse jwtResponse = authBusiness.callbackGoogle(authorizationCode);

        return ApiResponse.success(jwtResponse);
    }
}
