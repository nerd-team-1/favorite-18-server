package com.nerd.favorite18.core.api.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthGoogleAccessTokenResponse {
    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String id_token;

    public String getAccessToken() {
        return this.access_token;
    }
}
