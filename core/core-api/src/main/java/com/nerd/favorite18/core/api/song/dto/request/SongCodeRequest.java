package com.nerd.favorite18.core.api.song.dto.request;

import com.nerd.favorite18.core.enums.song.MachineType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SongCodeRequest {
    @NotBlank(message = "노래방 기기 종류는 필수입니다.")
    private MachineType machineType;

    @NotBlank(message = "노래번호는 필수입니다.")
    private String songNum;
}
