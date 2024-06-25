package com.nerd.favorite18.core.api.song.controller;

import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.song.business.SongBusiness;
import com.nerd.favorite18.core.api.song.dto.response.SongResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/songs")
public class SongController {
    private final SongBusiness songBusiness;


    /** 노래 리스트 페이지 조회, Admin과 동일기능, Required로 조율 */
    @GetMapping
    public ApiResponse<Page<SongResponse>> getSongs(
        @RequestParam(name = "keyword") String keyword,
        Pageable pageable
    ) {
        return ApiResponse.success(songBusiness.getSongListPage(keyword, pageable));
    }

    /** 노래 단건 조회 */
    @GetMapping("/{id}")
    public ApiResponse<SongResponse> getSong(@PathVariable("id") Long songId) {
        return ApiResponse.success(songBusiness.getSong(songId));
    }
}
