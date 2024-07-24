package com.nerd.favorite18.core.api.auth.model;

public class GoogleUserInfoMobile implements OAuth2UserInfo {
    private final UserInfo userInfo;

    public GoogleUserInfoMobile(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return userInfo.id;
    }

    @Override
    public String getSubId() {
        return getProvider() + "_" + getProviderId();
    }

    @Override
    public String getEmail() {
        return userInfo.email;
    }

    @Override
    public String getName() {
        return userInfo.name;
    }

    public String getThumbnail() {
        return userInfo.photo;
    }
}
