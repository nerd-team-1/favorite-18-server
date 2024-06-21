package com.nerd.favorite18.storage.db.core.analysis.entity;

import com.nerd.favorite18.storage.db.core.BaseEntity;
import com.nerd.favorite18.storage.db.core.song.entity.Song;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("노래방 점수 분석")
@Entity
@Table(name = "tbl_score")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisScore extends BaseEntity {

    @Comment("사용자 ID")
    @NotNull
    @JoinColumn(name = "USER_ID")
    @OneToOne(fetch =  FetchType.LAZY)
    private User requestAnalysisUser;

    @Comment("노래 ID")
    @NotNull
    @JoinColumn(name = "SONG_ID")
    @OneToOne(fetch =  FetchType.LAZY)
    private Song requestAnalysisSong;

    @Comment("녹음파일 경로")
    @Column(name = "M4A_FILE_PATH", nullable = false)
    private String m4aFilePath;

    @Comment("분석점수")
    @Column(name = "SCORE", nullable = false)
    private Integer analysisScore;

    @Builder
    private AnalysisScore(
        User requestAnalysisUser,
        Song requestAnalysisSong,
        String m4aFilePath,
        Integer analysisScore
    ) {
        this.requestAnalysisUser = requestAnalysisUser;
        this.requestAnalysisSong = requestAnalysisSong;
        this.m4aFilePath = m4aFilePath;
        this.analysisScore = analysisScore;
    }

    public static AnalysisScore of(User requestAnalysisUser, Song requestAnalysisSong, String m4aFilePath, Integer analysisScore) {
        isValidScore(analysisScore);

        return AnalysisScore.builder()
            .requestAnalysisUser(requestAnalysisUser)
            .requestAnalysisSong(requestAnalysisSong)
            .m4aFilePath(m4aFilePath)
            .analysisScore(analysisScore)
            .build();
    }

    // 추후 상수화 필요성
    private static void isValidScore(int score) {
        if (score >= 0 && score <= 100) {
            return;
        }
        throw new IllegalArgumentException("분석 점수가 정상 범주가 아닙니다.");
    }
}
