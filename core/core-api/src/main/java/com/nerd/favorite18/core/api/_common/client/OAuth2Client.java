package com.nerd.favorite18.core.api._common.client;

import com.nerd.favorite18.core.api.auth.dto.request.AuthGoogleAccessTokenRequest;
import com.nerd.favorite18.core.api.auth.dto.response.AuthGoogleAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuth2Client {
    private final RestTemplate restTemplate;

    @Value("${oauth2.google.authorization-uri}")
    private String googleAuthorizationUri;

    @Value("${oauth2.google.token-uri}")
    private String googleTokenUri;

    @Value("${oauth2.google.user-info-uri}")
    private String googleUserInfoUri;

    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String redirectUri;

    public URI getAuthorizationUri() {

        return UriComponentsBuilder.fromUriString(googleAuthorizationUri)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "email profile")
                .build()
                .toUri();
    }

    public AuthGoogleAccessTokenResponse getAccessToken(String authorizationCode) {
        AuthGoogleAccessTokenRequest request = AuthGoogleAccessTokenRequest.of(
                clientId,
                clientSecret,
                redirectUri,
                "authorization_code",
                authorizationCode
        );

        return restTemplate.postForObject(googleTokenUri, request, AuthGoogleAccessTokenResponse.class);
    }

    public Map<String, Object> getUserInfo(String accessToken) {
        String uri = UriComponentsBuilder.fromUriString(googleUserInfoUri)
                .queryParam("access_token", accessToken)
                .toUriString();

        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {}).getBody();
    }
}
