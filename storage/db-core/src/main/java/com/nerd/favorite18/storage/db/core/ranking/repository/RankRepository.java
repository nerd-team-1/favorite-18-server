package com.nerd.favorite18.storage.db.core.ranking.repository;

import com.nerd.favorite18.storage.db.core.ranking.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {
    Optional<Rank> findByRankDateOrderBySearchCntDesc(Date rankDate);

    //Song findByMachineTypeAndSongNum(String machineType, String songNum);
}
