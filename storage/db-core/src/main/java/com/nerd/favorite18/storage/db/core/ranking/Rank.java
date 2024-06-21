package com.nerd.favorite18.storage.db.core.ranking;

import com.nerd.favorite18.storage.db.core.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Table(name = "tbl_rank")
@Comment("인기곡")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rank extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SONG_ID")
    @Comment("노래 정보")
    private Song rankSong;

    @NotNull
    @CreationTimestamp
    @Comment("랭킹 날짜")
    private Date RankDate;

    @Column(length = 3)
    private String rank;

    private Long searchCnt;

    @Builder
    public Rank(Song rankSong,
                Date rankDate,
                String rank,
                Long searchCnt
    ) {
        this.rankSong = rankSong;
        this.RankDate = rankDate;
        this.rank = rank;
        this.searchCnt = searchCnt;
    }
}
