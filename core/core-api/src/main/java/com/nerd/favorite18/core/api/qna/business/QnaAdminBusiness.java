package com.nerd.favorite18.core.api.qna.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.qna.dto.QnaDto;
import com.nerd.favorite18.core.api.qna.dto.request.QnaUpdateRequest;
import com.nerd.favorite18.core.api.qna.dto.response.QnaResponse;
import com.nerd.favorite18.core.api.qna.service.QnaService;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Business
public class QnaAdminBusiness {
    private final QnaService qnaService;

    /**
     * [ 답변 전체 조회 ]
     * <br/>
     * 진행상태로 구분 가능한 답변 전체를 조회합니다. <br/>
     *
     * @param progressStatus 진행상태
     * @param pageable 페이지 객체
     * @return Q&A 리스트 with page
     */
    public Page<QnaResponse> getQnas(String progressStatus, Pageable pageable) {

        return qnaService.getAllQna(null, validateProgressStatus(progressStatus), pageable);
    }

    /**
     * [ 내 답변 전체 조회 ]
     * <br/>
     * 진행상태로 구분 가능한 내가 답변한 리스트를 조회합니다. <br/>
     *
     * @param userDto 관리자 정보
     * @param progressStatus 진행 상태
     * @param pageable 페이지 객체
     * @return 내가 답변한 Q&A 리스트 with page
     */
    public Page<QnaResponse> getMyQnas(UserDto userDto, String progressStatus, Pageable pageable) {

        return qnaService.getAllQna(userDto.getId(), validateProgressStatus(progressStatus), pageable);
    }

    /**
     * [ Q&A 업데이트 ]
     * <br/>
     * Q&A 의 진행상태와 답변을 업데이트 할 수 있습니다.<br/>
     *
     * @param userDto 관리자 정보
     * @param qnaId Q&A id
     * @param request 진행상태 or 답변
     */
    public QnaDto updateQna(UserDto userDto, Long qnaId, QnaUpdateRequest request) {
        return qnaService.updateQnaFromAdmin(userDto.getId(), qnaId, request);
    }

    private QnaProgressStatus validateProgressStatus(String progressStatus) {
        if (ObjectUtils.isEmpty(progressStatus)) {
            return null;
        }

        return QnaProgressStatus.valueOf(progressStatus);
    }
}
