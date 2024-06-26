package com.nerd.favorite18.core.api.qna.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.qna.converter.QnaConverter;
import com.nerd.favorite18.core.api.qna.converter.QnaQueryConverter;
import com.nerd.favorite18.core.api.qna.dto.QnaDto;
import com.nerd.favorite18.core.api.qna.dto.request.QnaAddRequest;
import com.nerd.favorite18.core.api.qna.dto.request.QnaUpdateRequest;
import com.nerd.favorite18.core.api.qna.dto.response.QnaResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.qna.dto.QnaQueryDto;
import com.nerd.favorite18.storage.db.core.qna.entity.Qna;
import com.nerd.favorite18.storage.db.core.qna.projection.QnaListResponse;
import com.nerd.favorite18.storage.db.core.qna.repository.QnaRepository;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import com.nerd.favorite18.storage.db.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QnaService {
    private final QnaRepository qnaRepository;
    private final QnaConverter qnaConverter;
    private final QnaQueryConverter qnaQueryConverter;

    private final UserRepository userRepository;

    /**
     * 관리자용 Q&A 전체 조회
     *
     * @param adminUserId 관리자 id
     * @param progressStatus Q&A 진행 상태
     * @param pageable 페이지 객체
     * @return Q&A 리스트 with page
     */
    @Transactional(readOnly = true)
    public Page<QnaResponse> getAllQna(Long adminUserId, QnaProgressStatus progressStatus, Pageable pageable) {
        final Page<QnaQueryDto> queryResult = qnaRepository.findQnasByAdminUserIdOrProgressStatus(adminUserId, progressStatus, pageable);

        List<QnaResponse> results = queryResult.getContent().stream()
                .map(qnaQueryConverter::toQnaResponse)
                .toList();

        return PageableExecutionUtils.getPage(results, pageable, queryResult::getTotalElements);
    }

    /**
     * 사용자용 내 Q&A 목록 조회
     *
     * @param userDto 사용자 정보
     * @param pageable 페이지 객체
     * @return 내 질문 리스트 with page
     */
    @Transactional(readOnly = true)
    public Page<QnaListResponse> getMyQna(UserDto userDto, Pageable pageable) {
        User userEntity = userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        final Page<QnaListResponse> qnas = qnaRepository.findAllByQnaUserOrderByCreatedAtDesc(userEntity, pageable);
        if (qnas == null) throw new CoreApiException(ErrorType.NULL_POINT);

        return qnas;
    }

    /**
     * 사용자용 내 Q&A 1건 조회
     *
     * @param userDto 사용자 정보
     * @param id Q&A id
     * @return 내 질문 1건
     */
    @Transactional(readOnly = true)
    public QnaDto getQna(UserDto userDto, Long id) {
        User userEntity = userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        Qna qnaEntity =  qnaRepository.findByIdAndQnaUserOrderByIdDesc(id, userEntity)
                .orElseThrow(() -> new CoreApiException(ErrorType.NULL_POINT));

        return qnaConverter.toDto(qnaEntity);
    }

    /**
     * 사용자용 Q&A 등록
     *
     * @param userDto 사용자 정보
     * @param request 질문 제목 및 내용
     * @return 등록된 답변
     */
    @Transactional
    public Qna insertQna(UserDto userDto, QnaAddRequest request) {
        User userEntity = userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        Qna qnaEntity = qnaConverter.toEntity(userEntity, request);

        return qnaRepository.save(qnaEntity);
    }

    /**
     * 관리자용 답변 업데이트
     * @param adminUserId 관리자 id
     * @param qnaId Q&A id
     * @param request 진행상태 or 답변
     */
    @Transactional
    public QnaDto updateQnaFromAdmin(Long adminUserId, Long qnaId, QnaUpdateRequest request) {
        User userEntity = userRepository.findFirstByIdAndStatusOrderByIdDesc(adminUserId, UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        Qna qnaEntity = qnaRepository.findByIdOrderByIdDesc(qnaId).orElseThrow(() -> new CoreApiException(ErrorType.QNA_NOT_FOUND));

        final Qna save = qnaRepository.save(update(userEntity, qnaEntity, request));

        return qnaConverter.toDto(save);
    }

    // 답변 업데이트 메소드
    private Qna update(User adminUser, Qna qnaEntity, QnaUpdateRequest request) {
        Class<? extends QnaUpdateRequest> requestClass = request.getClass();
        Field[] fields = requestClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(request);

                if (!ObjectUtils.isEmpty(value)) {
                    Field qnaField = Qna.class.getDeclaredField(field.getName());
                    qnaField.setAccessible(true);
                    qnaField.set(qnaEntity, value);

                    if (field.getName().equals("answerContent")) {
                        qnaEntity.updateAdminUser(adminUser);
                        qnaEntity.updateAnswerStatus(AnswerStatus.REPLIED);
                    }
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new CoreApiException(ErrorType.DEFAULT_ERROR, "Q&A 업데이트를 실패했습니다.");
            }
        }

        return qnaEntity;
    }
}
