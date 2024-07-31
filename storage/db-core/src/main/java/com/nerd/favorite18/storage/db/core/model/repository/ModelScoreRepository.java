package com.nerd.favorite18.storage.db.core.model.repository;

import com.nerd.favorite18.storage.db.core.model.entity.ModelScore;
import com.nerd.favorite18.storage.db.core.model.projection.ModelScoreProjection;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelScoreRepository extends JpaRepository<ModelScore, Long> {
    List<ModelScoreProjection> findAllByUserOrderByCreatedAtDesc(User userEntity);
}
