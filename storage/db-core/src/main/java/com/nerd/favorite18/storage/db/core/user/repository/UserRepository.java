package com.nerd.favorite18.storage.db.core.user.repository;

import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import com.nerd.favorite18.storage.db.core.user.projection.UserListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u ORDER BY u.id ASC")
    Page<UserListResponse> findAllOrderedById(Pageable pageable);
    Optional<User> findFirstBySubIdAndEmailOrderByIdDesc(String subId, String email);
    Optional<User> findFirstByIdAndStatusOrderByIdDesc(Long userId, UserStatus status);
}
