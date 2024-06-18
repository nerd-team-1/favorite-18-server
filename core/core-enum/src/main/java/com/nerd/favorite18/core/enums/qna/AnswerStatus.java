package com.nerd.favorite18.core.enums.qna;

import com.nerd.favorite18.core.enums.common.EnumMapperType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AnswerStatus implements EnumMapperType {
    NO_REPLY("답장 안함"),
    REPLIED("답장 완료");

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
