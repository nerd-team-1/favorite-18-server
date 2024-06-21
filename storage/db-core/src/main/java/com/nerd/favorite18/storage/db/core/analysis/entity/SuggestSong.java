package com.nerd.favorite18.storage.db.core.analysis.entity;

import com.nerd.favorite18.storage.db.core.BaseEntity;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("대화를 통한 곡 추천")
@Entity
@Table(name = "tbl_suggest")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuggestSong extends BaseEntity {

    @Comment("사용자 ID")
    @NotNull
    @JoinColumn(name = "USER_ID")
    @OneToOne(fetch =  FetchType.LAZY)
    private User suggestUser;

    @Comment("노래 ID")
    @NotNull
    @JoinColumn(name = "SUGGEST_SONG_ID")
    @ManyToOne(fetch =  FetchType.LAZY)
    private Song suggestSong;

    @Comment("감정지수")
    @Column(name = "EMOTION")
    private String emotion;

    // 대화내용
    @OneToMany(mappedBy = "suggestSong", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuggestSongConversation> conversations = new ArrayList<>();

    @Builder
    private SuggestSong(User suggestUser, Song suggestSong, String emotion) {
        this.suggestUser = suggestUser;
        this.suggestSong = suggestSong;
        this.emotion = emotion;
    }

    public void addConversation(String conversation) {
        conversations.add(SuggestSongConversation.of(this, conversation));
    }
}
