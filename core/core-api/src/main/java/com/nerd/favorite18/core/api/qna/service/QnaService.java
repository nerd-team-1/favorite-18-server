package com.nerd.favorite18.core.api.qna.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.qna.converter.QnaConverter;
import com.nerd.favorite18.core.api.qna.dto.QnaAddRequest;
import com.nerd.favorite18.core.api.qna.dto.QnaDto;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.qna.entity.Qna;
import com.nerd.favorite18.storage.db.core.qna.projection.QnaListResponse;
import com.nerd.favorite18.storage.db.core.qna.repository.QnaRepository;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import com.nerd.favorite18.storage.db.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class QnaService {
    private final QnaRepository qnaRepository;
    private final QnaConverter qnaConverter;

    private final UserRepository userRepository;

    public Page<QnaListResponse> getMyQna(UserDto userDto, Pageable pageable) {
        User userEntity = Optional.ofNullable(
                userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        final Page<QnaListResponse> qnas = qnaRepository.findAllByQnaUserOrderByCreatedAtDesc(userEntity, pageable);
        if (qnas == null) throw new CoreApiException(ErrorType.NULL_POINT);

        return qnas;
    }

    public QnaDto getQna(UserDto userDto, Long id) {
        User userEntity = Optional.ofNullable(
                userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        Qna qnaEntity =  qnaRepository.findByIdAndQnaUserOrderByIdDesc(id, userEntity)
                .orElseThrow(() -> new CoreApiException(ErrorType.NULL_POINT));

        return qnaConverter.toDto(qnaEntity);
    }

    public Qna insertQna(UserDto userDto, QnaAddRequest request) {
        User userEntity = Optional.ofNullable(
                userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        Qna qnaEntity = qnaConverter.toEntity(userEntity, request);

        return qnaRepository.save(qnaEntity);
    }
}
