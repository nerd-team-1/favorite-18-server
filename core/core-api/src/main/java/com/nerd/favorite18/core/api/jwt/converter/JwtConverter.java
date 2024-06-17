package com.nerd.favorite18.core.api.jwt.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api.jwt.dto.JwtRefreshResponse;
import com.nerd.favorite18.core.api.jwt.dto.JwtResponse;
import com.nerd.favorite18.core.api.jwt.model.Token;

import java.util.Objects;

@Converter
public class JwtConverter {
    public JwtResponse toResponse(Token accessToken, Token refreshToken) {
        Objects.requireNonNull(accessToken, () -> {throw new RuntimeException();});
        Objects.requireNonNull(refreshToken, () -> {throw new RuntimeException();});

        return JwtResponse.of(
                accessToken.getToken(),
                accessToken.getExpiredAt(),
                refreshToken.getToken(),
                refreshToken.getExpiredAt()
        );
    }

    public JwtRefreshResponse toResponse(Token accessToken) {
        Objects.requireNonNull(accessToken, () -> {throw new RuntimeException();});

        return JwtRefreshResponse.of(
                accessToken.getToken(),
                accessToken.getExpiredAt()
        );
    }
}
