package com.nerd.favorite18.core.api.auth.model;

public interface OAuth2UserInfo {
    String getProvider();
    String getProviderId();
    String getSubId();
    String getEmail();
    String getName();
}
