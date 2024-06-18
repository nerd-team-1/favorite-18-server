package com.nerd.favorite18.core.enums.song;

import com.nerd.favorite18.core.enums.common.EnumMapperType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MachineType implements EnumMapperType {
    TJ("TJ"), KY("금영");

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
