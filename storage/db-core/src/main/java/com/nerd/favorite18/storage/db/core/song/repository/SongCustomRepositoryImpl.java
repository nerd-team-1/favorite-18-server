package com.nerd.favorite18.storage.db.core.song.repository;

import static com.nerd.favorite18.storage.db.core.song.entity.QSong.song;
import static com.nerd.favorite18.storage.db.core.song.entity.QSongCode.songCode;
import static com.nerd.favorite18.storage.db.core.song.entity.QSongLike.songLike;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import com.nerd.favorite18.storage.db.core.song.dto.QSongCodeQueryDto;
import com.nerd.favorite18.storage.db.core.song.dto.QSongQueryDto;
import com.nerd.favorite18.storage.db.core.song.dto.SongQueryDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
public class SongCustomRepositoryImpl implements SongCustomRepository{
    private final JPAQueryFactory query;

    @Override
    public Page<SongQueryDto> searchPage(String keyword, Pageable pageable) {
        List<Long> totalIds = query.select(song.id)
            .from(song)
            .join(song.songCodes, songCode)
            .where(
                new BooleanBuilder()
                    .and(titleContains(keyword))
                    .or(artistContains(keyword))
            )
            .distinct()
            .fetch();

        if (totalIds.isEmpty()) {
            return Page.empty();
        }

        List<Long> resultIds = query.select(song.id)
            .from(song)
            .join(song.songCodes, songCode)
            .where(
                new BooleanBuilder()
                    .and(titleContains(keyword))
                    .or(artistContains(keyword))
            )
            .distinct()
            .orderBy(song.id.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<SongQueryDto> result = query.select(song)
            .from(song)
            .join(song.songCodes, songCode)
            .where(song.id.in(resultIds))
            .orderBy(song.id.asc())
            .distinct()
            .transform(
                groupBy(song.id).list(
                    new QSongQueryDto(
                        song.id,
                        song.title,
                        song.artist,
                        song.albumPictureUrl,
                        song.scoreCompareUrl,
                        song.songLikes.size(),
                        song.createdAt,
                        song.updatedAt,
                        list(
                            new QSongCodeQueryDto(
                                songCode.machineType,
                                songCode.songNum,
                                songCode.createdAt,
                                songCode.updatedAt
                            )
                        )
                    )
                )
            );

        return PageableExecutionUtils.getPage(result, pageable, totalIds::size);
    }

    private BooleanExpression titleContains(String keyword) {
        return ObjectUtils.isEmpty(keyword) ? null : song.title.contains(keyword);
    }

    private BooleanExpression artistContains(String keyword) {
        return ObjectUtils.isEmpty(keyword) ? null : song.artist.contains(keyword);
    }
}
