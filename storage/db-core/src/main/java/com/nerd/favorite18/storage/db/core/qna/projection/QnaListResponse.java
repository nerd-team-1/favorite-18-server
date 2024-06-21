package com.nerd.favorite18.storage.db.core.qna.projection;

import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.storage.db.core.BaseProjection;

public interface QnaListResponse extends BaseProjection {
    String getTitle();
    QnaProgressStatus getProgressStatus();
    AnswerStatus getAnswerStatus();
}
