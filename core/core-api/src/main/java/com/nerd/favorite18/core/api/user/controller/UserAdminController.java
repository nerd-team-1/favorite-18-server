package com.nerd.favorite18.core.api.user.controller;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.response.ApiResponse;
import com.nerd.favorite18.core.api.user.business.UserAdminBusiness;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserUpdateUserRoleRequest;
import com.nerd.favorite18.storage.db.core.user.projection.UserListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin-api/v1/user")
@RestController
public class UserAdminController {
    private final UserAdminBusiness userAdminBusiness;

    @GetMapping
    public ApiResponse<Page<UserListResponse>> adminUserList(@UserSession UserDto userDto, Pageable pageable) {
        final Page<UserListResponse> users = userAdminBusiness.adminUserList(pageable);

        return ApiResponse.success(users);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateUser(
            @UserSession UserDto userDto,
            @PathVariable("id") Long userId,
            @RequestBody UserUpdateUserRoleRequest request
    ) {
        userAdminBusiness.updateUser(userDto, userId, request);

        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@UserSession UserDto userDto, @PathVariable("id") Long userId) {
        userAdminBusiness.deleteUser(userDto, userId);

        return ApiResponse.success();
    }
}
