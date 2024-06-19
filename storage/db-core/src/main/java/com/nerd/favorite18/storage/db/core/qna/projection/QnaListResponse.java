package com.nerd.favorite18.storage.db.core.qna.projection;

import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;

import java.time.LocalDateTime;

public interface QnaListResponse {
    Long getId();
    String getTitle();
    QnaProgressStatus getProgressStatus();
    AnswerStatus getAnswerStatus();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}
