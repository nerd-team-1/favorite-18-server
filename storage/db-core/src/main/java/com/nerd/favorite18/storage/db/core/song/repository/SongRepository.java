package com.nerd.favorite18.storage.db.core.song.repository;


import com.nerd.favorite18.storage.db.core.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long>, SongCustomRepository {
    Optional<Song> findFirstByIdOrderByIdDesc(Long songId);
    boolean existsByCompareTitleAndArtist(String compareTitle, String artist);
}
