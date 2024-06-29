package com.nerd.favorite18.core.api.qna.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api.qna.dto.response.QnaResponse;
import com.nerd.favorite18.core.api.user.converter.UserQueryConverter;
import com.nerd.favorite18.storage.db.core.qna.dto.QnaQueryDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Converter
public class QnaQueryConverter {
    private final UserQueryConverter userQueryConverter;

    public QnaResponse toQnaResponse(QnaQueryDto dto) {

        return QnaResponse.builder()
                .id(dto.getId())
                .qnaUser(userQueryConverter.toUserResponse(dto.getQnaUser()))
                .title(dto.getTitle())
                .content(dto.getContent())
                .progressStatus(dto.getProgressStatus())
                .answerStatus(dto.getAnswerStatus())
                .adminUser(userQueryConverter.toUserResponse(dto.getAdminUser()))
                .answerContent(dto.getAnswerContent())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
