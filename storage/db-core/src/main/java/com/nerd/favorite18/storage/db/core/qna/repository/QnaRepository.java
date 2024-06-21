package com.nerd.favorite18.storage.db.core.qna.repository;

import com.nerd.favorite18.storage.db.core.qna.entity.Qna;
import com.nerd.favorite18.storage.db.core.qna.projection.QnaListResponse;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface QnaRepository extends JpaRepository<Qna, Long> {
    Page<QnaListResponse> findAllByQnaUserOrderByCreatedAtDesc(User qnaUser, Pageable pageable);
    Optional<Qna> findByIdAndQnaUserOrderByIdDesc(Long id, User qnaUser);
}
