package com.nerd.favorite18.core.enums.qna;

import com.nerd.favorite18.core.enums.common.EnumMapperType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QnaProgressStatus implements EnumMapperType {
    UNPROCESSED("미처리"),
    IN_PROGRESS("처리중"),
    COMPLETED("처리완료"),
    CLOSED("닫힘"),
    ON_HOLD("보류중");

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
