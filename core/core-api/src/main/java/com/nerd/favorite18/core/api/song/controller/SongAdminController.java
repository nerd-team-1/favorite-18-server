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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/songs")
public class SongAdminController {
    private final SongBusiness songBusiness;


    /** 노래 리스트 전체 조회 */


    /** 노래 수동 등록 */
    @PostMapping
    private ApiResponse<SongResponse> createSong(
        @UserSession UserDto user,
        @RequestBody SongCreateRequest songCreateRequest
    ) {
        return ApiResponse.success(songBusiness.createSong(user, songCreateRequest));
    }


    /** 노래 정보 수정 */
    @PutMapping
    private void updateSong(
        @UserSession UserDto user,
        @RequestBody SongUpdateRequest songUpdateRequest
    ) {
        songBusiness.updateSong(user, songUpdateRequest);
    }

    /** 노래 정보 삭제 */
    @DeleteMapping("/{id}")
    private ApiResponse<Void> deleteSong(@UserSession UserDto user, @PathVariable("id") Long songId) {
        songBusiness.deleteSong(user, songId);
        return ApiResponse.success();
    }
}
