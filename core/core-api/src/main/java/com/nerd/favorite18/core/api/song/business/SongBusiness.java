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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Business
@RequiredArgsConstructor
public class SongBusiness {
    private final SongSelectService songSelectService;
    private final SongManageService songManageService;

    @Transactional(readOnly = true)
    public SongResponse getSong(Long songId) {
        return songSelectService.getSong(songId);
    }

    @Transactional(readOnly = true)
    public Page<SongResponse> getSongListPage(String keyword, Pageable pageable) {
        return songSelectService.getSongListPage(keyword, pageable);
    }

    @Transactional
    public SongResponse createSong(SongCreateRequest request) {
        return songManageService.createSong(request);
    }

    @Transactional
    public SongResponse updateSong(SongUpdateRequest request) {
        return songManageService.updateSong(request);
    }

    @Transactional
    public void deleteSong(Long songId) {
        songManageService.deleteSong(songId);
    }
}
