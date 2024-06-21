package com.nerd.favorite18.storage.db.core.song.repository;

import com.nerd.favorite18.storage.db.core.song.entity.SongLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongLikeRepository extends JpaRepository<SongLike, Long> {

}
