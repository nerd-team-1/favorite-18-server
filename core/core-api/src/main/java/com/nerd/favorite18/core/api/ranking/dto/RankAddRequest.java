package com.nerd.favorite18.core.api.ranking.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RankAddRequest {
    private LocalDateTime today;
    private String machineType;

}
