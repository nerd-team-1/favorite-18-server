package com.nerd.favorite18.storage.db.core.song.repository;

import com.nerd.favorite18.storage.db.core.song.projection.SongLikeProjection;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.song.entity.SongLike;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongLikeRepository extends JpaRepository<SongLike, Long> {
    Page<SongLikeProjection> findAllBySongLikeUserOrderByCreatedAtDesc(User songLikeUser, Pageable pageable);
    void deleteBySongLikeUserAndSong(User songLikeUser, Song song);
}
