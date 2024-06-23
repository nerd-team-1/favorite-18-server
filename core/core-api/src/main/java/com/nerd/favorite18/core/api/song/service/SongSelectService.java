package com.nerd.favorite18.core.api.song.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.song.converter.SongConverter;
import com.nerd.favorite18.core.api.song.converter.SongQueryConverter;
import com.nerd.favorite18.core.api.song.dto.response.SongResponse;
import com.nerd.favorite18.storage.db.core.song.dto.SongQueryDto;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.song.repository.SongRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SongSelectService {
    private final SongRepository songRepository;
    private final SongConverter songConverter;
    private final SongQueryConverter songQueryConverter;

    public SongResponse getSong(Long songId) {
        final Song findEntity = songRepository.findById(songId)
            .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));

        return songConverter.toSongResponseWithFavoriteCountByEntity(findEntity);
    }

    public Page<SongResponse> getSongListPage(String keyword, Pageable pageable) {
        final Page<SongQueryDto> queryResult = songRepository.searchPage(keyword, pageable);

        List<SongResponse> resultList = queryResult.getContent().stream()
            .map(songQueryConverter::toSongResponse)
            .toList();

        return new PageImpl<>(resultList, pageable, queryResult.getTotalElements());
    }

}
