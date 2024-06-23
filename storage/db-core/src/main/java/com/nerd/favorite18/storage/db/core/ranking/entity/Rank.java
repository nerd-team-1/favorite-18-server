package com.nerd.favorite18.storage.db.core.ranking.entity;

import com.nerd.favorite18.storage.db.core.BaseEntity;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_rank")
@Comment("인기곡")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rank extends BaseEntity {

    @Comment("노래 정보")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SONG_ID")
    private Song rankSong;

    @Comment("랭킹 날짜")
    @NotNull
    @CreationTimestamp
    private LocalDateTime rankDate;

    @Comment("랭킹 순위")
    @Column(length = 3)
    private String rank;

    @Comment("검색 횟수")
    private Long searchCnt;

    @Builder
    public Rank(Song rankSong,
                LocalDateTime rankDate,
                String rank,
                Long searchCnt
    ) {
        this.rankSong = rankSong;
        this.rankDate = rankDate;
        this.rank = rank;
        this.searchCnt = searchCnt;
    }
}
