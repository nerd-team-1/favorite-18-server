package com.nerd.favorite18.storage.db.core.qna.repository;

import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.storage.db.core.qna.dto.QQnaQueryDto;
import com.nerd.favorite18.storage.db.core.qna.dto.QnaQueryDto;
import com.nerd.favorite18.storage.db.core.user.dto.QUserQueryDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.nerd.favorite18.storage.db.core.qna.entity.QQna.qna;
import static com.querydsl.core.group.GroupBy.groupBy;

@Slf4j
@RequiredArgsConstructor
public class QnaCustomRepositoryImpl implements QnaCustomRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<QnaQueryDto> findQnasByAdminUserIdOrProgressStatus(Long adminUserId, QnaProgressStatus progressStatus, Pageable pageable) {
        // 전체 ID 조회
        List<Long> totalIds = query.select(qna.id)
                .from(qna)
                .where(
                        new BooleanBuilder()
                            .and(adminUserIdContains(adminUserId))
                            .and(progressStatusContains(progressStatus))
                )
                .fetch();

        if (totalIds.isEmpty()) {
            return Page.empty();
        }

        // 페이지에 해당하는 ID 조회
        List<Long> resultIds = query.select(qna.id)
                .from(qna)
                .where(
                        new BooleanBuilder()
                                .and(adminUserIdContains(adminUserId))
                                .and(progressStatusContains(progressStatus))
                )
                .orderBy(qna.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 결과 조회
        List<QnaQueryDto> result = query.selectFrom(qna)
                .leftJoin(qna.qnaUser)
                .leftJoin(qna.adminUser)
                .where(qna.id.in(resultIds))
                .orderBy(qna.id.desc())
                .transform(
                        groupBy(qna.id).list(
                                new QQnaQueryDto(
                                        qna.id,
                                        new QUserQueryDto(
                                                qna.qnaUser.subId,
                                                qna.qnaUser.email,
                                                qna.qnaUser.name,
                                                qna.qnaUser.nickname,
                                                qna.qnaUser.birth,
                                                qna.qnaUser.gender,
                                                qna.qnaUser.thumbnail,
                                                qna.qnaUser.role,
                                                qna.qnaUser.status,
                                                qna.qnaUser.createdAt,
                                                qna.qnaUser.updatedAt
                                        ),
                                        qna.title,
                                        qna.content,
                                        qna.progressStatus,
                                        qna.answerStatus,
                                        new QUserQueryDto(
                                                qna.adminUser.subId,
                                                qna.adminUser.email,
                                                qna.adminUser.name,
                                                qna.adminUser.nickname,
                                                qna.adminUser.birth,
                                                qna.adminUser.gender,
                                                qna.adminUser.thumbnail,
                                                qna.adminUser.role,
                                                qna.adminUser.status,
                                                qna.adminUser.createdAt,
                                                qna.adminUser.updatedAt
                                        ),
                                        qna.answerContent,
                                        qna.createdAt,
                                        qna.updatedAt
                                )
                        )
                );

        return PageableExecutionUtils.getPage(result, pageable, totalIds::size);
    }

    private BooleanExpression adminUserIdContains(Long adminUserId) {
        return ObjectUtils.isEmpty(adminUserId) ? null : qna.adminUser.id.eq(adminUserId);
    }

    private BooleanExpression progressStatusContains(QnaProgressStatus progressStatus) {
        return ObjectUtils.isEmpty(progressStatus) ? null : qna.progressStatus.eq(progressStatus);
    }
}
