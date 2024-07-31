package com.nerd.favorite18.core.api.model.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api._common.utils.ConvertUtils;
import com.nerd.favorite18.core.api.model.converter.ModelConverter;
import com.nerd.favorite18.core.api.model.dto.response.ModelScoreListResponse;
import com.nerd.favorite18.core.api.model.model.ScoreResult;
import com.nerd.favorite18.core.api.song.dto.SongDto;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.model.entity.ModelScore;
import com.nerd.favorite18.storage.db.core.model.projection.ModelScoreProjection;
import com.nerd.favorite18.storage.db.core.model.repository.ModelScoreRepository;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.song.projection.SongProjection;
import com.nerd.favorite18.storage.db.core.song.repository.SongRepository;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import com.nerd.favorite18.storage.db.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ModelScoreService {
    private final ModelScoreRepository modelScoreRepository;
    private final ModelConverter modelConverter;

    private final UserRepository userRepository;
    private final SongRepository songRepository;

    @Transactional(readOnly = true)
    public List<ModelScoreListResponse> getScoreList(UserDto userDto) {
        User userEntity =  userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        final List<ModelScoreProjection> projections = modelScoreRepository.findAllByUserOrderByCreatedAtDesc(userEntity);

        return projections.stream().map(projection -> {
            SongProjection song = projection.getSong();

            return ModelScoreListResponse.of(
                    projection.getId(),
                    SongDto.of(song.getId(), song.getTitle(), song.getArtist(), song.getAlbumPictureUrl()),
                    projection.getScore(),
                    projection.getSimilarity(),
                    projection.getCreatedAt(),
                    projection.getUpdatedAt()
            );
        }).toList();
    }

    @Transactional
    public Long saveRecord(UserDto userDto, Long songId, String recordedFilename) {
        User userEntity =  userRepository.findFirstByIdAndStatusOrderByIdDesc(userDto.getId(), UserStatus.ACTIVE)
                .orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        Song songEntity = songRepository.findFirstByIdOrderByIdDesc(songId)
                .orElseThrow(() -> new CoreApiException(ErrorType.SONG_NOT_FOUND));

        final ModelScore modelScore = modelConverter.toEntity(userEntity, songEntity, recordedFilename);
        final ModelScore saveEntity = modelScoreRepository.save(modelScore);

        return saveEntity.getId();
    }

    @Transactional
    public void saveScore(Long modelScoreId, ScoreResult scoreResult, long analysisTime) {
        final Integer score = ConvertUtils.stringToInteger(scoreResult.getNormalizedScore());
        final Double similarity = ConvertUtils.stringToDouble(scoreResult.getCombinedSimilarity());

        final ModelScore entity = modelScoreRepository.findById(modelScoreId)
                .orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND));

        entity.applyUpdates(score, similarity, analysisTime);

        modelScoreRepository.save(entity);
    }
}
