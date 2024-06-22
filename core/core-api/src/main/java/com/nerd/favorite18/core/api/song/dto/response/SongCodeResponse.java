package com.nerd.favorite18.core.api.song.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nerd.favorite18.core.enums.song.MachineType;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class SongCodeResponse {
    private MachineType machineType;
    private String songCode;

    @Builder
    private SongCodeResponse(MachineType machineType, String songCode) {
        this.machineType = machineType;
        this.songCode = songCode;
    }
}
