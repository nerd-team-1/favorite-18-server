package com.nerd.favorite18.storage.db.core.song.repository;


import com.nerd.favorite18.storage.db.core.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
    boolean existsByCompareTitleAndArtist(String compareTitle, String artist);
}
