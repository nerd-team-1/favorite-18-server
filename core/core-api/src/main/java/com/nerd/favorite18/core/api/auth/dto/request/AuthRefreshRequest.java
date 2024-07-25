package com.nerd.favorite18.core.api.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRefreshRequest {
    private String refreshToken;
    private LocalDateTime refreshTokenExpiredAt;
}
