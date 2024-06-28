package com.nerd.favorite18.core.api.ranking.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RankAddRequest {
    private String title;
    private String artist;
    private Long searchCnt;
    private String machineType;

}
