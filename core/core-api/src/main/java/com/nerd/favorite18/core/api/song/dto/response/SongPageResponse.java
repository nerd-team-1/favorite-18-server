package com.nerd.favorite18.core.api.song.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nerd.favorite18.core.api._common.support.response.PageResponse;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class SongPageResponse extends PageResponse {
    private final List<SongResponse> songs;

    public SongPageResponse(
        int nowPage,
        int nowLimit,
        int pageSize,
        int totalRecordCount,
        int totalPageCount,
        boolean existPrevPage,
        boolean existNextPage,
        List<SongResponse> songs
    ) {
        super(
            nowPage,
            nowLimit,
            pageSize,
            totalRecordCount,
            totalPageCount,
            existPrevPage,
            existNextPage
        );
        this.songs = songs;
    }

    public static SongPageResponse of(
        List<SongResponse> songs,
        int nowPage,
        int nowLimit,
        int pageSize,
        int totalRecordCount,
        int totalPageCount,
        boolean existPrevPage,
        boolean existNextPage
    ){
        SongPageResponse response = new SongPageResponse(
            nowPage,
            nowLimit,
            pageSize,
            totalRecordCount,
            totalPageCount,
            existPrevPage,
            existNextPage,
            songs
        );

        return response;
    }
}
