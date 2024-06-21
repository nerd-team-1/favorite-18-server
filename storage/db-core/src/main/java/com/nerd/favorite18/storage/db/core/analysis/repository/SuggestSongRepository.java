package com.nerd.favorite18.storage.db.core.analysis.repository;

import com.nerd.favorite18.storage.db.core.analysis.entity.SuggestSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestSongRepository extends JpaRepository<SuggestSong, Long> {

}
