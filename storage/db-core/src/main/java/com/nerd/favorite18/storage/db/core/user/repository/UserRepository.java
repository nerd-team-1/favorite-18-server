package com.nerd.favorite18.storage.db.core.user.repository;

import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findFirstBySubIdAndEmailOrderByIdDesc(String subId, String email);
    User findFirstByIdAndStatusOrderByIdDesc(Long userId, UserStatus status);
}
