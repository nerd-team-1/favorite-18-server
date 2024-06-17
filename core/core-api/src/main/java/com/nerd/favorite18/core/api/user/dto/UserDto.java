package com.nerd.favorite18.core.api.user.dto;

import com.nerd.favorite18.storage.db.core.user.type.UserGender;
import com.nerd.favorite18.storage.db.core.user.type.UserRole;
import com.nerd.favorite18.storage.db.core.user.type.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String subId;
    private String email;
    private String name;
    private String nickname;
    private String birth;
    private UserGender gender;
    private String thumbnail;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDto of(
            Long id,
            String subId,
            String email,
            String name,
            String nickname,
            String birth,
            UserGender gender,
            String thumbnail,
            UserRole role,
            UserStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new UserDto(
                id,
                subId,
                email,
                name,
                nickname,
                birth,
                gender,
                thumbnail,
                role,
                status,
                createdAt,
                updatedAt
        );
    }
}
