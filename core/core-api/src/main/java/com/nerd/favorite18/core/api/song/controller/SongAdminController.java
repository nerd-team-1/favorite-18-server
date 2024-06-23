package com.nerd.favorite18.core.api.song.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.song.business.SongBusiness;
import com.nerd.favorite18.core.api.song.dto.request.SongCreateRequest;
import com.nerd.favorite18.core.api.song.dto.request.SongUpdateRequest;
import com.nerd.favorite18.core.api.song.dto.response.SongResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-api/v1/songs")
public class SongAdminController {
    private final SongBusiness songBusiness;

    /** 노래 리스트 전체 조회 */
    @GetMapping
    public ApiResponse<Page<SongResponse>> getSongs(
        @RequestParam(name = "keyword", required = false) String keyword,
        Pageable pageable
    ) {
        return ApiResponse.success(songBusiness.getSongListPage(keyword, pageable));
    }

    /* 노래 단건 조회 **/
    @GetMapping("/{id}")
    public ApiResponse<SongResponse> getSong(@PathVariable("id") Long songId) {
        return ApiResponse.success(songBusiness.getSong(songId));
    }


    /** 노래 수동 등록 */
    @PostMapping
    public ApiResponse<SongResponse> createSong(
        @UserSession UserDto user,
        @RequestBody @Validated SongCreateRequest songCreateRequest
    ) {
        return ApiResponse.success(songBusiness.createSong(songCreateRequest));
    }


    /** 노래 정보 수정 */
    @PutMapping
    public ApiResponse<Void> updateSong(
        @UserSession UserDto user,
        @RequestBody @Validated SongUpdateRequest songUpdateRequest
    ) {
        songBusiness.updateSong(songUpdateRequest);
        return ApiResponse.success();
    }

    /** 노래 정보 삭제 */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSong(
        @UserSession UserDto user,
        @PathVariable("id") Long songId
    ) {
        songBusiness.deleteSong(songId);
        return ApiResponse.success();
    }
}
