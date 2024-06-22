package com.nerd.favorite18.core.api.song.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.song.converter.SongConverter;
import com.nerd.favorite18.core.api.song.dto.request.SongCreateRequest;
import com.nerd.favorite18.core.api.song.dto.request.SongUpdateRequest;
import com.nerd.favorite18.core.api.song.dto.response.SongResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.song.repository.SongRepository;
import com.nerd.favorite18.storage.db.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongManageService {

    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final SongConverter songConverter;

    @Transactional
    public SongResponse createSong(UserDto user, SongCreateRequest request) {
        isValidRequestUserAdmin(user);

        final Song requestEntity = songConverter.toSongEntityByCreateRequest(request);

        // 중복된 노래 존재여부 확인
        boolean isExistSong = songRepository.existsByCompareTitleAndArtist(
            requestEntity.getCompareTitle(),
            requestEntity.getArtist()
        );

        if (isExistSong) {
            throw new CoreApiException(ErrorType.SONG_DUPLICATE);
        }

        final Song resultEntity = songRepository.save(requestEntity);

        return songConverter.toSongResponseByEntity(resultEntity);
    }

    @Transactional
    public SongResponse updateSong(UserDto user, SongUpdateRequest request) {
        isValidRequestUserAdmin(user);

        final Song updateEntity = songRepository.findById(request.getSongId())
            .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));

        updateSongEntity(updateEntity, request);

        final Song resultEntity = songRepository.save(updateEntity);

        return songConverter.toSongResponseByEntity(resultEntity);
    }


    @Transactional
    public void deleteSong(UserDto user, Long songId) {
        isValidRequestUserAdmin(user);

        final Song song = songRepository.findById(songId)
            .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));

        songRepository.delete(song);
    }

    /**
     * 요청자가 관리지인지, 일반사용자인지 여부를 확인한다.
     */
    private void isValidRequestUserAdmin(UserDto user) {
        userRepository.findFirstByIdAndStatusOrderByIdDesc(user.getId(), UserStatus.ACTIVE)
            .ifPresentOrElse(
                requestUser -> {
                    if (!requestUser.isUserRoleAdmin()) {
                        throw new CoreApiException(ErrorType.FORBIDDEN);
                    }
                },
                () -> {
                    // {}로 감싸지 않으면, Exception이 발생하지 않음.
                    throw new CoreApiException(ErrorType.USER_NOT_FOUND);
                }
            );
    }

    private void updateSongEntity(Song song, SongUpdateRequest request) {
        if (!request.getAlbumPictureUrl().equals(song.getAlbumPictureUrl())) {
            song.setAlbumPictureUrl(request.getAlbumPictureUrl());
        }

        // 가사의 경우 내용이 많기 때문에, 비교없이 그냥 업데이트를 하는 쪽으로 한다.
        if (StringUtils.hasText(request.getLyrics())) {
            song.setLyrics(request.getLyrics());
        }

        if (request.getScoreCompareUrl().equals(song.getScoreCompareUrl())) {
            song.setScoreCompareUrl(request.getScoreCompareUrl());
        }

        if (request.getSongCodes().isEmpty()) {
            return;
        }

        if (song.getSongCodes().isEmpty()) {
            // 기존에 등록된 노래 코드가 없는 경우, Request의 값을 바로 넣어준다.
            request.getSongCodes()
                .stream()
                .map(songConverter::toSongCodeEntityByCreateRequest)
                .forEach(song::addSongCode);
        } else {
            // 만약, 동일한 회사로 등록된 코드가 존재하면, Update, 없으면 Insert를 진행한다.
            request.getSongCodes()
                .forEach(
                    requestSongCode ->
                        song.getSongCodeByMachineType(requestSongCode.getMachineType())
                            .ifPresentOrElse(
                                songCode -> songCode.setSongNum(requestSongCode.getSongNum()),
                                () -> song.addSongCode(songConverter.toSongCodeEntityByCreateRequest(requestSongCode))
                            )
                );
        }
    }

}
