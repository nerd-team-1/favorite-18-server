package com.nerd.favorite18.core.api.auth.dto.request;

import com.nerd.favorite18.core.api.auth.model.UserInfo;
import com.nerd.favorite18.core.enums.user.UserGender;
import lombok.Getter;

@Getter
public class AuthSignupGoogleRequest {
    String birth;
    UserGender gender;
    UserInfo userInfo;
}
