package com.nerd.favorite18.core.api.jwt.service;

import com.nerd.favorite18.core.api.jwt.helper.TokenHelper;
import com.nerd.favorite18.core.api.jwt.model.Token;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final TokenHelper tokenHelper;

    public Token issueAccessToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);

        return tokenHelper.issueAccessToken(data);
    }

    public Token issueRefreshToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);

        return tokenHelper.issueRefreshToken(data);
    }

    public Long validationToken(String token) {
        final Map<String, Object> map = tokenHelper.validationTokenWithThrow(token);
        final Object userId = map.get("userId");

        Objects.requireNonNull(userId, () -> {throw new CoreApiException(ErrorType.NULL_POINT);});

        return Long.parseLong(userId.toString());
    }
}
