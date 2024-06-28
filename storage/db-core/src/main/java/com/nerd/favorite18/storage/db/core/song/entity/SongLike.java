package com.nerd.favorite18.storage.db.core.song.entity;

import com.nerd.favorite18.storage.db.core.BaseEntity;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("사용자의 노래 좋아요")
@Entity
@Table(
        name = "SONG_LIKE",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"USER_ID", "SONG_ID"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongLike extends BaseEntity {

    @Comment("사용자 ID")
    @NotNull
    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User songLikeUser;

    @Comment("노래 ID")
    @NotNull
    @JoinColumn(name = "SONG_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Song song;


    @Builder
    private SongLike(User songLikeUser, Song song) {
        this.songLikeUser = songLikeUser;
        this.song = song;
    }

    public static SongLike of(User user, Song song) {

        return SongLike.builder()
            .songLikeUser(user)
            .song(song)
            .build();
    }
}
