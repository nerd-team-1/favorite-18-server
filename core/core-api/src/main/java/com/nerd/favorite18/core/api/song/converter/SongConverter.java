package com.nerd.favorite18.core.api.song.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api._common.utils.FavoriteStringUtils;
import com.nerd.favorite18.core.api.song.dto.request.SongCodeRequest;
import com.nerd.favorite18.core.api.song.dto.request.SongCreateRequest;
import com.nerd.favorite18.core.api.song.dto.response.SongCodeResponse;
import com.nerd.favorite18.core.api.song.dto.response.SongResponse;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.song.entity.SongCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

@Slf4j
@Converter
@RequiredArgsConstructor
public class SongConverter {
    public Song toSongEntityByCreateRequest(SongCreateRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new CoreApiException(ErrorType.BAD_REQUEST);
        }

        Song result =  Song.builder()
            .title(request.getTitle())
            .artist(request.getArtist())
            .albumPictureUrl(request.getAlbumPictureUrl())
            .lyrics(request.getLyrics())
            .scoreCompareUrl(request.getScoreCompareUrl())
            .compareTitle(FavoriteStringUtils.removeWhitespace(request.getTitle()))
            .build();

        request.getSongCodes()
            .stream()
            .map(this::toSongCodeEntityByCreateRequest)
            .forEach(result::addSongCode);

        return result;
    }

    public SongCode toSongCodeEntityByCreateRequest(SongCodeRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            throw new CoreApiException(ErrorType.BAD_REQUEST);
        }

        return SongCode.builder()
            .machineType(request.getMachineType())
            .songNum(request.getSongNum())
            .build();
    }

    public SongResponse toSongResponseByEntity(Song song) {
        return SongResponse.builder()
            .songId(song.getId())
            .title(song.getTitle())
            .artist(song.getArtist())
            .albumUrl(song.getAlbumPictureUrl())
            .lyrics(song.getLyrics())
            .scoreCompareUrl(song.getScoreCompareUrl())
            .machineCodes(toSongCodeResponseListByEntity(song.getSongCodes()))
            .build();
    }

    public SongResponse toSongResponseWithFavoriteCountByEntity(Song song) {
        return SongResponse.builder()
            .songId(song.getId())
            .title(song.getTitle())
            .artist(song.getArtist())
            .albumUrl(song.getAlbumPictureUrl())
            .lyrics(song.getLyrics())
            .scoreCompareUrl(song.getScoreCompareUrl())
            .machineCodes(toSongCodeResponseListByEntity(song.getSongCodes()))
            .totalFavoriteCount(song.getTotalLikeCnt())
            .build();
    }

    public SongCodeResponse toSongCodeResponseByEntity(SongCode songCode) {
        return SongCodeResponse.builder()
            .machineType(songCode.getMachineType())
            .songCode(songCode.getSongNum())
            .build();
    }

    private List<SongCodeResponse> toSongCodeResponseListByEntity(List<SongCode> songCodes) {
        return songCodes.stream()
            .map(this::toSongCodeResponseByEntity)
            .toList();
    }

}
