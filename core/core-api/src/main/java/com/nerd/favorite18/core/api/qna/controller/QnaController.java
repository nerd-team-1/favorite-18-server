package com.nerd.favorite18.core.api.qna.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.qna.business.QnaBusiness;
import com.nerd.favorite18.core.api.qna.dto.request.QnaAddRequest;
import com.nerd.favorite18.core.api.qna.dto.QnaDto;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.storage.db.core.qna.entity.Qna;
import com.nerd.favorite18.storage.db.core.qna.projection.QnaListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/qna")
@RestController
public class QnaController {
    private final QnaBusiness qnaBusiness;

    @GetMapping
    public ApiResponse<Page<QnaListResponse>> getMyQnaList(@UserSession UserDto userDto, Pageable pageable) {
        Page<QnaListResponse> response = qnaBusiness.getMyQnaList(userDto, pageable);

        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<QnaDto> getQna(@UserSession UserDto userDto, @PathVariable Long id) {
        QnaDto response = qnaBusiness.getQna(userDto, id);

        return ApiResponse.success(response);
    }

    @PostMapping
    public ApiResponse<Qna> addQna(@UserSession UserDto userDto, @RequestBody QnaAddRequest request) {
        Qna response = qnaBusiness.addQna(userDto, request);

        return ApiResponse.success(response);
    }
}
