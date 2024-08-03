package com.nerd.favorite18.core.api.song.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.song.business.SongLikeBusiness;
import com.nerd.favorite18.core.api.song.dto.SongLikeDto;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "4.2 [노래-좋아요]", description = "노래 좋아요 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
@RestController
public class SongLikeController {
    private final SongLikeBusiness songLikeBusiness;

    @Operation(summary = "나의 좋아요 목록", description = "좋아요한 노래 목록을 조회한다.")
    @GetMapping
    public ApiResponse<Page<SongLikeDto>> myLikeList(@UserSession UserDto userDto, Pageable pageable) {
        final Page<SongLikeDto> response = songLikeBusiness.myLikeList(userDto, pageable);

        return ApiResponse.success(response);
    }

    @Operation(summary = "좋아요 여부 확인", description = "노래 좋아요 여부를 확인한다.")
    @GetMapping("/confirm")
    public ApiResponse<List<Long>> confirmLikeList (
        @UserSession UserDto userDto,
        @RequestParam("songIds") List<Long> songIds
    ) {
        final List<Long> response = songLikeBusiness.confirmLikeList(userDto, songIds);
        return ApiResponse.success(response);
    }

    @Operation(summary = "좋아요 추가", description = "노래 좋아요를 한다.")
    @PostMapping("/{id}")
    public ApiResponse<Void> like(@UserSession UserDto userDto, @PathVariable Long id) {
        songLikeBusiness.like(userDto, id);

        return ApiResponse.success();
    }

    @Operation(summary = "좋아요 취소", description = "노래 좋아요를 취소한다.")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> unLike(@UserSession UserDto userDto, @PathVariable Long id) {
        songLikeBusiness.unLike(userDto, id);

        return ApiResponse.success();
    }
}
