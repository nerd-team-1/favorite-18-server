package com.nerd.favorite18.core.api.ranking.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.qna.dto.QnaAddRequest;
import com.nerd.favorite18.core.api.ranking.dto.RankAddRequest;
import com.nerd.favorite18.core.api.ranking.dto.RankDto;
import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.storage.db.core.qna.entity.Qna;
import com.nerd.favorite18.storage.db.core.ranking.entity.Rank;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.user.entity.User;

import java.util.Optional;

@Converter
public class RankConverter {
    public RankDto toDto(Rank entity) {
       /* public Rank toEntity(Song songEntity, RankAddRequest request) {

            return Optional.ofNullable(request)
                    .map(it -> Rank.builder()
                            .qnaUser(userEntity)
                            .title(request.getTitle())
                            .content(request.getContent())
                            .progressStatus(QnaProgressStatus.UNPROCESSED)
                            .answerStatus(AnswerStatus.NO_REPLY)
                            .build())
                    .orElseThrow(() -> new CoreApiException(ErrorType.NULL_POINT));
        }*/

        return RankDto.of(
                entity.getId(),
                entity.getRankSong(),
                entity.getRankDate(),
                entity.getRank(),
                entity.getSearchCnt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
