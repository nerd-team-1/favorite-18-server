package com.nerd.favorite18.core.api.song.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.song.business.SongLikeBusiness;
import com.nerd.favorite18.core.api.song.dto.SongLikeDto;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
@RestController
public class SongLikeController {
    private final SongLikeBusiness songLikeBusiness;

    @GetMapping
    public ApiResponse<Page<SongLikeDto>> myLikeList(@UserSession UserDto userDto, Pageable pageable) {
        final Page<SongLikeDto> response = songLikeBusiness.myLikeList(userDto, pageable);

        return ApiResponse.success(response);
    }

    @PostMapping("/{id}")
    public ApiResponse<Void> like(@UserSession UserDto userDto, @PathVariable Long id) {
        songLikeBusiness.like(userDto, id);

        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> unLike(@UserSession UserDto userDto, @PathVariable Long id) {
        songLikeBusiness.unLike(userDto, id);

        return ApiResponse.success();
    }
}
