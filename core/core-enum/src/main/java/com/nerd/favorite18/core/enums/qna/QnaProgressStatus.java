package com.nerd.favorite18.core.enums.qna;

import com.nerd.favorite18.core.enums.common.EnumMapperType;
import lombok.RequiredArgsConstructor;

/**
 * QNA의 진행여부 상태
 */

@RequiredArgsConstructor
public enum QnaProgressStatus implements EnumMapperType {
    UNPROCESSED("미처리"), // 아직 담당자가 답변, 작업확인등을 하지 않은 상태
    IN_PROGRESS("처리중"), // 담당자가 현재 처리중인 상태의 답변들로, 오래 걸리는 경우 진행을 하고 있다는 알림으로 사용
    ON_HOLD("보류중"),     // 현재 해당작업은 확인은 하였으나, 진행을 하지 못하고 있는 경우에 사용
    COMPLETED("처리완료");  // 답변 및 처리까지 완료된 QNA의 처리 상태

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
