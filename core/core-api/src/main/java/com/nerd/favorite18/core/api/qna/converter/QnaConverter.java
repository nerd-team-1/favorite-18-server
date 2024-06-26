package com.nerd.favorite18.core.api.qna.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.qna.dto.request.QnaAddRequest;
import com.nerd.favorite18.core.api.qna.dto.QnaDto;
import com.nerd.favorite18.core.api.user.converter.UserConverter;
import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.storage.db.core.qna.entity.Qna;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class QnaConverter {
    private final UserConverter userConverter;

    public Qna toEntity(User userEntity, QnaAddRequest request) {

        return Optional.ofNullable(request)
                .map(it -> Qna.builder()
                        .qnaUser(userEntity)
                        .title(request.getTitle())
                        .content(request.getContent())
                        .progressStatus(QnaProgressStatus.UNPROCESSED)
                        .answerStatus(AnswerStatus.NO_REPLY)
                        .build())
                .orElseThrow(() -> new CoreApiException(ErrorType.NULL_POINT));
    }

    public QnaDto toDto(Qna entity) {

        return QnaDto.of(
                entity.getId(),
                userConverter.toDto(entity.getQnaUser()),
                entity.getTitle(),
                entity.getContent(),
                entity.getProgressStatus(),
                entity.getAnswerStatus(),
                userConverter.toDto(entity.getAdminUser()),
                entity.getAnswerContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
