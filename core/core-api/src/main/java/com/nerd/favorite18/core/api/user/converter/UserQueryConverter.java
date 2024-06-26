package com.nerd.favorite18.core.api.user.converter;

import com.nerd.favorite18.core.api._common.annotation.Converter;
import com.nerd.favorite18.core.api.user.dto.response.UserResponse;
import com.nerd.favorite18.storage.db.core.user.dto.UserQueryDto;

@Converter
public class UserQueryConverter {
    public UserResponse toUserResponse(UserQueryDto dto) {

        return UserResponse.builder()
                .subId(dto.getSubId())
                .email(dto.getEmail())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .birth(dto.getBirth())
                .gender(dto.getGender())
                .thumbnail(dto.getThumbnail())
                .role(dto.getRole())
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
