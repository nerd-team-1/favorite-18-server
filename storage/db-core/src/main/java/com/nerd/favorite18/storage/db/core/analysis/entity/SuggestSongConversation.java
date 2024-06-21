package com.nerd.favorite18.storage.db.core.analysis.entity;

import com.nerd.favorite18.storage.db.core.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("곡추천 분석 대화 목록")
@Entity
@Table(name = "tbl_suggest_conversation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuggestSongConversation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private SuggestSong suggestSong;

    @Comment("대화내용")
    @Column(name = "CONVERSATION", nullable = false)
    private String conversation;

    @Builder
    private SuggestSongConversation(SuggestSong suggestSong, String conversation) {
        this.suggestSong = suggestSong;
        this.conversation = conversation;
    }

    public static SuggestSongConversation of(
        SuggestSong suggestSong, String conversation
    ) {
        return SuggestSongConversation.builder()
            .suggestSong(suggestSong)
            .conversation(conversation)
            .build();
    }
}
