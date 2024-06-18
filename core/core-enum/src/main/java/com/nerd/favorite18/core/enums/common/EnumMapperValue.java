package com.nerd.favorite18.core.enums.common;

import lombok.Getter;

/**
 * EnumMapperType을 변환한다.
 */

@Getter
public class EnumMapperValue {

    private String code;
    private String value;

    public EnumMapperValue(EnumMapperType enumMapperType) {
        this.code = enumMapperType.getCode();
        this.value = enumMapperType.getValue();
    }

    @Override
    public String toString() {
        return String.format("""
            {
                "code" : %s,
                "title" : %s
            }""", code, value
        );

    }
}
