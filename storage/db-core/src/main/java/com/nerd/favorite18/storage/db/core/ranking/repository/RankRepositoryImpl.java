package com.nerd.favorite18.storage.db.core.ranking.repository;

import com.nerd.favorite18.core.enums.song.MachineType;
import com.nerd.favorite18.storage.db.core.ranking.dto.QRankQueryDto;
import com.nerd.favorite18.storage.db.core.ranking.dto.RankQueryDto;
import com.nerd.favorite18.storage.db.core.song.dto.QSongCodeQueryDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;

import static com.nerd.favorite18.storage.db.core.ranking.entity.QRank.rank;
import static com.nerd.favorite18.storage.db.core.song.entity.QSong.song;
import static com.nerd.favorite18.storage.db.core.song.entity.QSongCode.songCode;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class RankRepositoryImpl implements RankCustomRepository {
    private final JPAQueryFactory query;

    final
    @Override
    public List<RankQueryDto> findAllByRankDateAndMachineType(LocalDate rankDate, MachineType machineType) {
        return query.select(Projections.constructor( RankQueryDto.class,
                        rank.id,
                        song.title,
                        song.artist,
                        song.albumPictureUrl,
                        rank.rankDate,
                        rank.ranking,
                        rank.searchCnt,
                        rank.createdAt,
                        rank.updatedAt,
                        songCode.songNum
                ))
                .from(rank)
                .join(rank.rankSong, song)
                .join(song.songCodes, songCode)
                .where(
                        new BooleanBuilder()
                                .and(rankDateEq(rankDate)
                                .and(machineTypeEq(machineType)))
                )
                .orderBy(rank.searchCnt.desc())
                .fetch()
                ;
    }

    private BooleanExpression rankDateEq(LocalDate rankDate) {
        return ObjectUtils.isEmpty(rankDate) ?  null : rank.rankDate.eq(rankDate);
    }

    private BooleanExpression machineTypeEq(MachineType machineType) {
        return ObjectUtils.isEmpty(machineType) ? null : songCode.machineType.eq(machineType);
    }
}
