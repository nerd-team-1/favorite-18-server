package com.nerd.favorite18.core.api._common.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2Client {
    private final static String AUTHORIZATION_URI = "https://accounts.google.com/o/oauth2/auth";
    private final static String TOKEN_URI = "https://oauth2.googleapis.com/token";
    private final static String USER_INFO_URI = "https://www.googleapis.com/oauth2/v3/userinfo";

    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String redirectUri;

    public URI getAuthorizationUri() {
        return UriComponentsBuilder.fromUriString(AUTHORIZATION_URI)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "email profile")
                .build()
                .toUri();
    }

    public Map<String, String> getAccessToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> request = new HashMap<>();

        request.put("client_id", clientId);
        request.put("client_secret", clientSecret);
        request.put("redirect_uri", redirectUri);
        request.put("grant_type", "authorization_code");
        request.put("code", authorizationCode);

        return restTemplate.postForObject(TOKEN_URI, request, Map.class);
    }

    public Map<String, Object> getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = UriComponentsBuilder.fromUriString(USER_INFO_URI)
                .queryParam("access_token", accessToken)
                .toUriString();

        return restTemplate.getForObject(uri, Map.class);
    }
}
