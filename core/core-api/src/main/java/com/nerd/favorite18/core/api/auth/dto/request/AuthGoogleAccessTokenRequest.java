package com.nerd.favorite18.core.api.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthGoogleAccessTokenRequest {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;
    private String code;

    public static AuthGoogleAccessTokenRequest of(
            String clientId,
            String clientSecret,
            String redirectUri,
            String grantType,
            String code
    ) {
        return new AuthGoogleAccessTokenRequest(clientId, clientSecret, redirectUri, grantType, code);
    }
}
