package com.nerd.favorite18.core.api.song.converter;


import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api.song.dto.response.SongCodeResponse;
import com.nerd.favorite18.core.api.song.dto.response.SongResponse;
import com.nerd.favorite18.storage.db.core.song.dto.SongCodeQueryDto;
import com.nerd.favorite18.storage.db.core.song.dto.SongQueryDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
@RequiredArgsConstructor
public class SongQueryConverter {

    public SongResponse toSongResponse(SongQueryDto songQueryDto) {
        return SongResponse.builder()
            .songId(songQueryDto.getSongId())
            .title(songQueryDto.getTitle())
            .artist(songQueryDto.getArtist())
            .albumUrl(songQueryDto.getAlbumUrl())
            .scoreCompareUrl(songQueryDto.getScoreCompareUrl())
            .createdAt(songQueryDto.getCreatedAt())
            .updatedAt(songQueryDto.getUpdatedAt())
            .machineCodes(toSongCodeResponse(songQueryDto.getMachineCodes()))
            .totalFavoriteCount(songQueryDto.getTotalFavoriteCount())
            .build();
    }

    private List<SongCodeResponse> toSongCodeResponse(List<SongCodeQueryDto> machineCodes) {
        return machineCodes.stream()
            .map(machineCode -> SongCodeResponse.builder()
                .machineType(machineCode.getMachineType())
                .songCode(machineCode.getSongCode())
                .createdAt(machineCode.getCreatedAt())
                .updatedAt(machineCode.getUpdatedAt())
                .build())
            .toList();
    }
}
