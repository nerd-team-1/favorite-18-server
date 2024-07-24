package com.nerd.favorite18.core.api.ranking.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nerd.favorite18.storage.db.core.song.entity.SongCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RankAddRequest {
    private Long songId;
    private String albumUrl;
    private String title;
    private String artist;
    private LocalDate rankDate;
    private String ranking;
    private Long searchCnt;
    private String machineType;

}
