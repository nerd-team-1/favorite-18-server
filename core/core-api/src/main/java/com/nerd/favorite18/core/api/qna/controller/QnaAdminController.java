package com.nerd.favorite18.core.api.qna.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.qna.business.QnaAdminBusiness;
import com.nerd.favorite18.core.api.qna.dto.QnaDto;
import com.nerd.favorite18.core.api.qna.dto.request.QnaUpdateRequest;
import com.nerd.favorite18.core.api.qna.dto.response.QnaResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "3.2 [Q&A(관리자)]", description = "관리자만 사용가능한 Q&A API")
@RequiredArgsConstructor
@RequestMapping("/admin-api/v1/qna")
@RestController
public class QnaAdminController {
    private final QnaAdminBusiness qnaAdminBusiness;

    @Operation(summary = "Q&A 목록 조회", description = "Q&A 목록을 조회한다.")
    @GetMapping
    public ApiResponse<Page<QnaResponse>> getQnas(
        @UserSession UserDto userDto,
        @RequestParam(required = false) QnaProgressStatus progressStatus,
        Pageable pageable
    ) {
        Page<QnaResponse> response = qnaAdminBusiness.getQnas(progressStatus, pageable);

        return ApiResponse.success(response);
    }

    @Operation(summary = "내 Q&A 목록 조회", description = "내 Q&A 목록을 조회한다.")
    @GetMapping("/me")
    public ApiResponse<Page<QnaResponse>> getMyQnas(
        @UserSession UserDto userDto,
        @RequestParam(required = false) QnaProgressStatus progressStatus,
        Pageable pageable
    ) {
        Page<QnaResponse> response = qnaAdminBusiness.getMyQnas(userDto, progressStatus, pageable);

        return ApiResponse.success(response);
    }

    @Operation(summary = "Q&A 정보 변경", description = "Q&A정보를 수정한다.")
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
