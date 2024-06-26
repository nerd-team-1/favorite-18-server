package com.nerd.favorite18.core.api.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nerd.favorite18.core.enums.user.UserGender;
import com.nerd.favorite18.core.enums.user.UserRole;
import com.nerd.favorite18.core.enums.user.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
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

    @Builder
    public UserResponse(
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
        this.id = id;
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
