package com.nerd.favorite18.core.api.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRefreshResponse {
    private String accessToken;
    private LocalDateTime accessTokenExpiredAt;

    public static JwtRefreshResponse of(
            String accessToken,
            LocalDateTime accessTokenExpiredAt
    ) {
        return new JwtRefreshResponse(
                accessToken,
                accessTokenExpiredAt
        );
    }
}
