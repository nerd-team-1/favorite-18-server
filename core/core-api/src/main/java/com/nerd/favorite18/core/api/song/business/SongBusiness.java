package com.nerd.favorite18.core.api.song.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.song.dto.request.SongCreateRequest;
import com.nerd.favorite18.core.api.song.dto.request.SongUpdateRequest;
import com.nerd.favorite18.core.api.song.dto.response.SongResponse;
import com.nerd.favorite18.core.api.song.service.SongSelectService;
import com.nerd.favorite18.core.api.song.service.SongManageService;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Business
@RequiredArgsConstructor
public class SongBusiness {
    private final SongSelectService songSelectService;
    private final SongManageService songManageService;

    @Transactional
    public SongResponse createSong(UserDto user, SongCreateRequest request) {
        return songManageService.createSong(user, request);
    }

    @Transactional
    public SongResponse updateSong(UserDto user, SongUpdateRequest request) {
        return songManageService.updateSong(user,request);
    }

    @Transactional
    public void deleteSong(UserDto user, Long songId) {
        songManageService.deleteSong(user, songId);
    }
}
