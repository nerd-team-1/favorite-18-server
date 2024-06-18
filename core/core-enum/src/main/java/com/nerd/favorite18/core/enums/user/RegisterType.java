package com.nerd.favorite18.core.enums.user;

import com.nerd.favorite18.core.enums.common.EnumMapperType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RegisterType  implements EnumMapperType {
    GOOGLE("구글"),
    NAVER("네이버"),
    KAKAO("카카오");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
