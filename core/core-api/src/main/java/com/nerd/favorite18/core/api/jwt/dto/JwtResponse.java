package com.nerd.favorite18.core.api.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private LocalDateTime accessTokenExpiredAt;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiredAt;

    public static JwtResponse of(
            String accessToken,
            LocalDateTime accessTokenExpiredAt,
            String refreshToken,
            LocalDateTime refreshTokenExpiredAt
    ) {
        return new JwtResponse(
                accessToken,
                accessTokenExpiredAt,
                refreshToken,
                refreshTokenExpiredAt
        );
    }
}
