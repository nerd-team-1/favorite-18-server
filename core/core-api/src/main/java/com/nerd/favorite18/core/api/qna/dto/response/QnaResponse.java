package com.nerd.favorite18.core.api.qna.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nerd.favorite18.core.api.user.dto.response.UserResponse;
import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QnaResponse {
    private Long id;
    private UserResponse qnaUser;
    private String title;
    private String content;
    private QnaProgressStatus progressStatus;
    private AnswerStatus answerStatus;
    private UserResponse adminUser;
    private String answerContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public QnaResponse(
            Long id,
            UserResponse qnaUser,
            String title,
            String content,
            QnaProgressStatus progressStatus,
            AnswerStatus answerStatus,
            UserResponse adminUser,
            String answerContent,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.qnaUser = qnaUser;
        this.title = title;
        this.content = content;
        this.progressStatus = progressStatus;
        this.answerStatus = answerStatus;
        this.adminUser = adminUser;
        this.answerContent = answerContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
