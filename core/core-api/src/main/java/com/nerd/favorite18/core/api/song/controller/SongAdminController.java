package com.nerd.favorite18.core.api.song.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.song.business.SongBusiness;
import com.nerd.favorite18.core.api.song.dto.request.SongCreateRequest;
import com.nerd.favorite18.core.api.song.dto.request.SongUpdateRequest;
import com.nerd.favorite18.core.api.song.dto.response.SongResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "4.3 [노래-관리자용]", description = "노래 정보")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/v1/songs")
public class SongAdminController {
    private final SongBusiness songBusiness;

    /** 노래 리스트 전체 조회 */
    @Operation(summary = "노래 리스트 전체 조회", description = "노래 리스트 전체를 조회한다.")
    @GetMapping
    public ApiResponse<Page<SongResponse>> getSongs(
        @RequestParam(name = "keyword", required = false) String keyword,
        Pageable pageable
    ) {
        return ApiResponse.success(songBusiness.getSongListPage(keyword, pageable));
    }

    /* 노래 단건 조회 **/
    @Operation(summary = "노래 단건 조회", description = "노래 단건을 조회한다.")
    @GetMapping("/{id}")
    public ApiResponse<SongResponse> getSong(@PathVariable("id") Long songId) {
        return ApiResponse.success(songBusiness.getSong(songId));
    }


    /** 노래 수동 등록 */
    @Operation(summary = "노래 수동 등록", description = "노래를 직접으로 등록한다.")
    @PostMapping
    public ApiResponse<SongResponse> createSong(
        @UserSession UserDto user,
        @RequestBody @Validated SongCreateRequest songCreateRequest
    ) {
        return ApiResponse.success(songBusiness.createSong(songCreateRequest));
    }


    /** 노래 정보 수정 */
    @Operation(summary = "노래 정보 수정", description = "노래 정보를 수정한다.")
    @PutMapping
    public ApiResponse<Void> updateSong(
        @UserSession UserDto user,
        @RequestBody @Validated SongUpdateRequest songUpdateRequest
    ) {
        songBusiness.updateSong(songUpdateRequest);
        return ApiResponse.success();
    }

    /** 노래 정보 삭제 */
    @Operation(summary = "노래 정보 삭제", description = "노래 정보를 삭제한다.")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSong(
        @UserSession UserDto user,
        @PathVariable("id") Long songId
    ) {
        songBusiness.deleteSong(songId);
        return ApiResponse.success();
    }
}
