package com.nerd.favorite18.storage.db.core.song.repository;

import com.nerd.favorite18.storage.db.core.song.dto.SongQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongCustomRepository {

    Page<SongQueryDto> searchPage(String keyword, Pageable pageable);
}
