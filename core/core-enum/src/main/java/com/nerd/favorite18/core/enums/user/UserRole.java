package com.nerd.favorite18.core.enums.user;

import com.nerd.favorite18.core.enums.common.EnumMapperType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole implements EnumMapperType {
    USER("사용자"),
    ADMIN("관리자");

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
