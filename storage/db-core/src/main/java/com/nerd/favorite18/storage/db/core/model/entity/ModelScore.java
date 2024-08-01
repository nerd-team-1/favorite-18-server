package com.nerd.favorite18.storage.db.core.model.entity;

import com.nerd.favorite18.storage.db.core.BaseEntity;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.ObjectUtils;

@Comment("노래 점수 분석")
@Entity
@Table(name = "tbl_model_score")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModelScore extends BaseEntity {
    @Comment("사용자 ID")
    @NotNull
    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Comment("노래 ID")
    @NotNull
    @JoinColumn(name = "SONG_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Song song;

    @Comment("분석 대상 이름")
    private String recordedFilename;

    @Comment("총 점수")
    private Integer score;

    @Comment("음정 분석치")
    private Double tune;

    @Comment("유사도 분석치")
    private Double similarity;

    @Comment("분석 소요 시간")
    private long analysisTime;

    @Builder
    public ModelScore(User user, Song song, String recordedFilename, Integer score, Double tune, Double similarity, long analysisTime) {
        this.user = user;
        this.song = song;
        this.recordedFilename = recordedFilename;
        this.score = score;
        this.tune = tune;
        this.similarity = similarity;
        this.analysisTime = analysisTime;
    }

    public void updateScore(Integer score) {
        this.score = score;
    }
    public void updateTune(Double tune) {
        this.tune = tune;
    }

    public void updateSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public void updateAnalysisTime(long analysisTime) {
        this.analysisTime = analysisTime;
    }

    public void applyUpdates(Integer score, Double tune, Double similarity, long analysisTime) {
        if (!ObjectUtils.isEmpty(score)) {
            updateScore(score);
        }

        if (!ObjectUtils.isEmpty(tune)) {
            updateTune(tune);
        }

        if (!ObjectUtils.isEmpty(similarity)) {
            updateSimilarity(similarity);
        }

        if (!ObjectUtils.isEmpty(analysisTime)) {
            updateAnalysisTime(analysisTime);
        }
    }
}
