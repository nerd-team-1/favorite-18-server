package com.nerd.favorite18.storage.db.core.ranking.repository;

import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {
    Optional<Rank> findByRankDateOrderBySearchCntDesc(LocalDateTime rankDate, String machineType);
}
