package com.nerd.favorite18.core.api.model.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.model.business.ModelScoreBusiness;
import com.nerd.favorite18.core.api.model.dto.request.ModelScoreRequest;
import com.nerd.favorite18.core.api.model.dto.request.ModelUploadRequest;
import com.nerd.favorite18.core.api.model.dto.response.ModelScoreListResponse;
import com.nerd.favorite18.core.api.model.dto.response.ModelScoreResponse;
import com.nerd.favorite18.core.api.model.dto.response.ModelUploadResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "2.1 [모델 점수]", description = "모델 점수 분석")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/model")
@RestController
public class ModelScoreController {
    private final ModelScoreBusiness modelScoreBusiness;

    // 내 점수 분석 히스토리
    @Operation(summary = "내 점수 분석 히스토리", description = "내 점수 분석 히스토리를 조회한다.")
    @GetMapping("/score")
    public ApiResponse<List<ModelScoreListResponse>> getMyScoreList(@UserSession UserDto userDto) {
        final List<ModelScoreListResponse> response = modelScoreBusiness.getMyScoreList(userDto);

        return ApiResponse.success(response);
    }

    // 녹음 파일 서버 업로드
    @Operation(summary = "녹음 파일 서버 업로드", description = "녹음 파일을 서버에 업로드한다.")
    @PostMapping("/record")
    public ApiResponse<ModelUploadResponse> uploadFile(
            @UserSession UserDto userDto,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "data") ModelUploadRequest request
    ) {
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.endsWith(".m4a")) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "유효한 m4a 파일을 업로드하세요.");
        }

        final ModelUploadResponse response = modelScoreBusiness.saveFile(userDto, file, request);

        return ApiResponse.success(response);
    }

    @Operation(summary = "녹음 파일 서버 업로드", description = "모바일에서 녹음 파일을 서버에 업로드한다.")
    @PostMapping("/record/mobile")
    public ApiResponse<ModelUploadResponse> uploadFile2(
            @UserSession UserDto userDto,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "data") String id
    ) {
        log.info("file: {}", file.getOriginalFilename());
        log.info("data: {}", id);

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.endsWith(".m4a")) {
            throw new CoreApiException(ErrorType.DEFAULT_ERROR, "유효한 m4a 파일을 업로드하세요.");
        }

        Long songId = Long.parseLong(id);
        final ModelUploadResponse response = modelScoreBusiness.saveFile(userDto, file, new ModelUploadRequest(songId));

        return ApiResponse.success(response);
    }

    // 녹음 파일 점수 분석
    @Operation(summary = "녹음 파일 점수 분석", description = "녹음 파일을 분석하여 점수를 반환한다.")
    @PostMapping("/score")
    public ApiResponse<ModelScoreResponse> scoreModel(
            @RequestBody ModelScoreRequest request
            ) {
        final ModelScoreResponse response = modelScoreBusiness.scoreModel(request);

        return ApiResponse.success(response);
    }
}
