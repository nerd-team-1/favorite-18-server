package com.nerd.favorite18.core.api.qna.dto;

import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QnaDto {
    private Long id;
    private UserDto qnaUser;
    private String title;
    private String content;
    private QnaProgressStatus progressStatus;
    private AnswerStatus answerStatus;
    private UserDto adminUser;
    private String answerContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static QnaDto of(
            Long id,
            UserDto qnaUser,
            String title,
            String content,
            QnaProgressStatus progressStatus,
            AnswerStatus answerStatus,
            UserDto adminUser,
            String answerContent,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new QnaDto(
                id,
                qnaUser,
                title,
                content,
                progressStatus,
                answerStatus,
                adminUser,
                answerContent,
                createdAt,
                updatedAt
        );
    }
}
