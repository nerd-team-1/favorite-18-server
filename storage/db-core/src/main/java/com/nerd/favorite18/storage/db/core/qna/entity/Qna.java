package com.nerd.favorite18.storage.db.core.qna.entity;

import com.nerd.favorite18.core.enums.qna.AnswerStatus;
import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.storage.db.core.BaseEntity;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "tbl_qna")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends BaseEntity {
    @Comment("문의자 ID")
    @NotNull
    @JoinColumn(name = "QNA_USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User qnaUser;

    @Comment("문의 제목")
    @Column(length = 100, nullable = false)
    private String title;

    @Comment("문의 내용")
    @Column(nullable = false)
    @Lob
    private String content;

    @Comment("진행 상태 : UNPROCESSED, IN_PROGRESS, ON_HOLD, COMPLETED")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QnaProgressStatus progressStatus;

    @Comment("답변 상태 : NO_REPLY, REPLIED")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;

    @Comment("답변자 ID")
    @JoinColumn(name = "ADMIN_USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User adminUser;

    @Comment("답변 내용")
    @Column
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

    public void updateAnswerStatus(AnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
    }

    public void updateAdminUser(User adminUser) {
        this.adminUser = adminUser;
    }
}
