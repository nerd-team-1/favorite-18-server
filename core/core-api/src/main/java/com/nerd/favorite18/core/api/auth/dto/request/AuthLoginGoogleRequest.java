package com.nerd.favorite18.core.api.auth.dto.request;

import com.nerd.favorite18.core.api.auth.model.UserInfo;
import lombok.Getter;

@Getter
public class AuthLoginGoogleRequest {
    String authCode;
    UserInfo userInfo;
}
