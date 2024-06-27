package com.nerd.favorite18.core.api.qna.dto.request;

import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QnaUpdateRequest {
    private final QnaProgressStatus progressStatus;
    private final String answerContent;

    public static QnaUpdateRequest of(QnaProgressStatus progressStatus, String answerContent) {

        return new QnaUpdateRequest(progressStatus, answerContent);
    }
}
