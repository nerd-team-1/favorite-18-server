package com.nerd.favorite18.storage.db.core.user.dto;

import com.nerd.favorite18.core.enums.user.UserGender;
import com.nerd.favorite18.core.enums.user.UserRole;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserQueryDto {
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

    @QueryProjection
    public UserQueryDto(
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
        this.subId = subId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.thumbnail = thumbnail;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
