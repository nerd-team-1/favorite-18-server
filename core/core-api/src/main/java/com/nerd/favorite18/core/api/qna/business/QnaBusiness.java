package com.nerd.favorite18.core.api.qna.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.qna.dto.QnaAddRequest;
import com.nerd.favorite18.core.api.qna.dto.QnaDto;
import com.nerd.favorite18.core.api.qna.service.QnaService;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.storage.db.core.qna.entity.Qna;
import com.nerd.favorite18.storage.db.core.qna.projection.QnaListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Business
public class QnaBusiness {
    private final QnaService qnaService;

    public Page<QnaListResponse> getMyQnaList(UserDto userDto, Pageable pageable) {

        return qnaService.getMyQna(userDto, pageable);
    }

    public QnaDto getQna(UserDto userDto, Long id) {

        return qnaService.getQna(userDto, id);
    }

    public Qna addQna(UserDto userDto, QnaAddRequest request) {

        return qnaService.insertQna(userDto, request);
    }
}
