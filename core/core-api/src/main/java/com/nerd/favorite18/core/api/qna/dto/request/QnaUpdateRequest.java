package com.nerd.favorite18.core.api.qna.dto.request;

import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QnaUpdateRequest {
    private QnaProgressStatus progressStatus;
    private String answerContent;

    public static QnaUpdateRequest of(QnaProgressStatus progressStatus, String answerContent) {

        return new QnaUpdateRequest(progressStatus, answerContent);
    }
}
