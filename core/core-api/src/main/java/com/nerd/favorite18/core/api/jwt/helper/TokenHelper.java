package com.nerd.favorite18.core.api.jwt.helper;

import com.nerd.favorite18.core.api.jwt.model.Token;

import java.util.Map;

public interface TokenHelper {
    Token issueAccessToken(Map<String, Object> data);
    Token issueRefreshToken(Map<String, Object> data);
    Map<String, Object> validationTokenWithThrow(String token);
}
