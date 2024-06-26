package com.nerd.favorite18.core.api.user.dto;

import com.nerd.favorite18.core.enums.user.UserGender;
import com.nerd.favorite18.core.enums.user.UserRole;
import com.nerd.favorite18.core.enums.user.UserStatus;
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

    public UserDto(Long id, UserRole role) {
        this.id = id;
        this.role = role;
    }

    public static UserDto of(Long id, UserRole role) {
        return new UserDto(id, role);
    }

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
