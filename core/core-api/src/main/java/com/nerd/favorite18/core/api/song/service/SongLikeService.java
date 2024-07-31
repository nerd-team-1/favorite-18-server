package com.nerd.favorite18.core.api.song.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.song.converter.SongLikeConverter;
import com.nerd.favorite18.core.api.song.dto.SongDto;
import com.nerd.favorite18.core.api.song.dto.SongLikeDto;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.song.projection.SongLikeProjection;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.song.entity.SongLike;
import com.nerd.favorite18.storage.db.core.song.projection.SongProjection;
import com.nerd.favorite18.storage.db.core.song.repository.SongLikeRepository;
import com.nerd.favorite18.storage.db.core.song.repository.SongRepository;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import com.nerd.favorite18.storage.db.core.user.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class SongLikeService {
    private final SongLikeRepository songLikeRepository;
    private final SongLikeConverter songLikeConverter;

    private final UserRepository userRepository;
    private final SongRepository songRepository;

    @Transactional(readOnly = true)
    public Page<SongLikeDto> getMySongLikeList(UserDto userDto, Pageable pageable) {
        User userEntity =  userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        final Page<SongLikeProjection> projections = songLikeRepository.findAllBySongLikeUserOrderByCreatedAtDesc(userEntity, pageable);

        if (ObjectUtils.isEmpty(projections)) {
            throw new CoreApiException(ErrorType.NULL_POINT);
        }

        return projections.map(page -> {
                SongProjection song = page.getSong();
                
                return SongLikeDto.of(
                    page.getId(),
                    SongDto.of(song.getId(), song.getTitle(), song.getArtist(), song.getAlbumPictureUrl()),
                    page.getCreatedAt(),
                    page.getUpdatedAt()
                );
            }
        );
    }

    @Transactional(readOnly = true)
    public List<Long> confirmLikeList(UserDto userDto, List<Long> songIds) {
        User userEntity =  userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        final List<SongLikeProjection> projections = songLikeRepository.findAllBySongLikeUserAndSongIdIn(userEntity, songIds);
        if (ObjectUtils.isEmpty(projections)) {
            return Collections.emptyList();
        }

        return projections.stream()
            .map(songProjection -> songProjection.getSong().getId())
            .toList();
    }

    @Transactional
    public void insertSongLike(UserDto userDto, Long songId) {
        User userEntity =  userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        Song songEntity = songRepository.findFirstByIdOrderByIdDesc(songId)
                .orElseThrow(() -> new CoreApiException(ErrorType.SONG_NOT_FOUND));

        SongLike songLikeEntity = songLikeConverter.toEntity(userEntity, songEntity);

        songLikeRepository.save(songLikeEntity);
    }

    @Transactional
    public void deleteSongLike(UserDto userDto, Long songId) {
        User userEntity =  userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        Song songEntity = songRepository.findFirstByIdOrderByIdDesc(songId)
                .orElseThrow(() -> new CoreApiException(ErrorType.SONG_NOT_FOUND));

        songLikeRepository.deleteBySongLikeUserAndSong(userEntity, songEntity);
    }
}
