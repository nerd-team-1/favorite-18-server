package com.nerd.favorite18.storage.db.core.qna.dto;

import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.storage.db.core.user.dto.UserQueryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QnaQueryDto {
    private Long id;
    private UserQueryDto qnaUser;
    private String title;
    private String content;
    private QnaProgressStatus progressStatus;
    private AnswerStatus answerStatus;
    private UserQueryDto adminUser;
    private String answerContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @QueryProjection
    public QnaQueryDto(
            Long id,
            UserQueryDto qnaUser,
            String title,
            String content,
            QnaProgressStatus progressStatus,
            AnswerStatus answerStatus,
            UserQueryDto adminUser,
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
