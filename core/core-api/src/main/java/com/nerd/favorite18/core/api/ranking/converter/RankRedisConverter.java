package com.nerd.favorite18.core.api.ranking.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.ranking.dto.RankRedisDto;
import com.nerd.favorite18.core.api.ranking.dto.request.RankAddRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

@Slf4j
@Converter
@RequiredArgsConstructor
public class RankRedisConverter {
    private final ObjectMapper objectMapper;

    public RankRedisDto toDto(RankAddRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new CoreApiException(ErrorType.BAD_REQUEST);
        }

        return RankRedisDto.of(
                request.getSongId(),
                request.getAlbumUrl(),
                request.getTitle(),
                request.getArtist(),
                request.getRankDate(),
                request.getRanking(),
                request.getSearchCnt()
        );
    }

    public RankAddRequest toRequest(String json) {
        try {
            return objectMapper.readValue(json, RankAddRequest.class);
        } catch (IOException e) {
            throw new CoreApiException(ErrorType.BAD_REQUEST, e);
        }
    }
}
