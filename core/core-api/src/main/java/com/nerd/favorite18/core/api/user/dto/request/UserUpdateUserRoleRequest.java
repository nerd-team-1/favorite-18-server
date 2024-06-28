package com.nerd.favorite18.core.api.user.dto.request;

import com.nerd.favorite18.core.enums.user.UserRole;
import lombok.Getter;

@Getter
public class UserUpdateUserRoleRequest {
    private UserRole role;
}
