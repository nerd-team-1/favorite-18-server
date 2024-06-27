package com.nerd.favorite18.storage.db.core.ranking.repository;

import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {

    @Query("SELECT r FROM Rank r JOIN SongCode s ON r.rankSong = s.song WHERE r.rankDate = :rankDate AND s.machineType = :machineType ORDER BY r.searchCnt DESC")
    Optional<List<Rank>> findByRankDateAndMachineTyperOrderBySearchCntDesc(@Param("rankDate") LocalDate rankDate, MachineType machineType);
}
