package com.nerd.favorite18.core.api.user.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserRegisterRequest;
import com.nerd.favorite18.core.enums.user.UserRole;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import java.util.Optional;

@Converter
public class UserConverter {
    public User toEntity(UserRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> User.builder()
                        .subId(request.getSubId())
                        .email(request.getEmail())
                        .name(request.getName())
                        .thumbnail(request.getThumbnail())
                        .role(UserRole.USER)
                        .status(UserStatus.ACTIVE)
                        .build())
                .orElseThrow(NullPointerException::new);
    }

    public UserDto toDto(User entity) {

        return UserDto.of(
                entity.getId(),
                entity.getSubId(),
                entity.getEmail(),
                entity.getName(),
                entity.getNickname(),
                entity.getBirth(),
                entity.getGender(),
                entity.getThumbnail(),
                entity.getRole(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
