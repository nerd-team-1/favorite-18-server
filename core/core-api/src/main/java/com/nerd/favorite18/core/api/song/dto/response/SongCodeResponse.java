package com.nerd.favorite18.core.api.song.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nerd.favorite18.core.enums.song.MachineType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class SongCodeResponse {
    private MachineType machineType;
    private String songCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    private SongCodeResponse(MachineType machineType, String songCode, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        this.machineType = machineType;
        this.songCode = songCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
