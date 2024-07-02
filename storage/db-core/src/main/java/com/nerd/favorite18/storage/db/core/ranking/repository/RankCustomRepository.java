package com.nerd.favorite18.storage.db.core.ranking.repository;

import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.storage.db.core.ranking.dto.RankQueryDto;

import java.time.LocalDate;
import java.util.List;

public interface RankCustomRepository {
    List<RankQueryDto> findAllByRankDateAndMachineType(LocalDate rankDate, MachineType machineType);
}
