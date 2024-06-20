package com.nerd.favorite18.storage.db.core.user.repository;

import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findBySubIdAndEmailAndStatusOrderByIdDesc(String subId, String email, UserStatus status);
    Optional<User> findFirstByIdAndStatusOrderByIdDesc(Long userId, UserStatus status);
}
