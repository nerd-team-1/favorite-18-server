package com.nerd.favorite18.storage.db.core.qna.repository;

import com.nerd.favorite18.core.enums.qna.QnaProgressStatus;
import com.nerd.favorite18.storage.db.core.qna.dto.QnaQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaCustomRepository {
    Page<QnaQueryDto> findQnasByAdminUserIdOrProgressStatus(Long adminUserId, QnaProgressStatus status, Pageable pageable);
}
