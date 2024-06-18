package com.nerd.favorite18.core.enums.user;

import com.nerd.favorite18.core.enums.common.EnumMapperType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserGender implements EnumMapperType {
    MAN("남자"),
    WOMEN("여자");

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
