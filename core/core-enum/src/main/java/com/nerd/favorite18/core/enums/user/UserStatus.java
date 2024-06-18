package com.nerd.favorite18.core.enums.user;

import com.nerd.favorite18.core.enums.common.EnumMapperType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus implements EnumMapperType {
    NOT_ACTIVE("비활성"),
    ACTIVE("활성"),
    SUSPENSION("정지"),
    DELETE("삭제");

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
