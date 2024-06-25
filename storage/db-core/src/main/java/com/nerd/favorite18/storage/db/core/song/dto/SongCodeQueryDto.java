package com.nerd.favorite18.storage.db.core.song.dto;

import com.nerd.favorite18.core.enums.song.MachineType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SongCodeQueryDto {
    private MachineType machineType;
    private String songCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @QueryProjection
    public SongCodeQueryDto(
        MachineType machineType,
        String songCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.machineType = machineType;
        this.songCode = songCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
