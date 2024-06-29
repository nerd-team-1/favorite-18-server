package com.nerd.favorite18.core.api.qna.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.qna.business.QnaAdminBusiness;
import com.nerd.favorite18.core.api.qna.dto.QnaDto;
import com.nerd.favorite18.core.api.qna.dto.request.QnaUpdateRequest;
import com.nerd.favorite18.core.api.qna.dto.response.QnaResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin-api/v1/qna")
@RestController
public class QnaAdminController {
    private final QnaAdminBusiness qnaAdminBusiness;

    @GetMapping
    public ApiResponse<Page<QnaResponse>> getQnas(
            @UserSession UserDto userDto,
            @RequestParam(required = false) QnaProgressStatus progressStatus,
            Pageable pageable
    ) {
        Page<QnaResponse> response = qnaAdminBusiness.getQnas(progressStatus, pageable);

        return ApiResponse.success(response);
    }

    @GetMapping("/me")
    public ApiResponse<Page<QnaResponse>> getMyQnas(
            @UserSession UserDto userDto,
            @RequestParam(required = false) QnaProgressStatus progressStatus,
            Pageable pageable
    ) {
        Page<QnaResponse> response = qnaAdminBusiness.getMyQnas(userDto, progressStatus, pageable);

        return ApiResponse.success(response);
    }

    @PutMapping("/{id}")
    public ApiResponse<QnaDto> updateQna(
            @UserSession UserDto userDto,
            @PathVariable("id") Long qnaId,
            @RequestBody QnaUpdateRequest request
    ) {
        final QnaDto response = qnaAdminBusiness.updateQna(userDto, qnaId, request);

        return ApiResponse.success(response);
    }
}
