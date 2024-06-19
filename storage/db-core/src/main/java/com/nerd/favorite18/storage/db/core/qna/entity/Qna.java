package com.nerd.favorite18.storage.db.core.qna.entity;

import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.storage.db.core.BaseEntity;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "tbl_qna")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends BaseEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QNA_USER_ID")
    private User qnaUser;

    @Column(length = 100, nullable = false)
    private String title;

    @NotNull
    @Lob
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private QnaProgressStatus progressStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_USER_ID")
    private User adminUser;

    @Lob
    private String answerContent;

    @Builder
    public Qna(
            User qnaUser,
            String title,
            String content,
            QnaProgressStatus progressStatus,
            AnswerStatus answerStatus,
            User adminUser,
            String answerContent
    ) {
        this.qnaUser = qnaUser;
        this.title = title;
        this.content = content;
        this.progressStatus = progressStatus;
        this.answerStatus = answerStatus;
        this.adminUser = adminUser;
        this.answerContent = answerContent;
    }
}
