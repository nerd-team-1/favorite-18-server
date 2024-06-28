package com.nerd.favorite18.core.api.user.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserUpdateUserRoleRequest;
import com.nerd.favorite18.core.api.user.service.UserService;
import com.nerd.favorite18.storage.db.core.user.projection.UserListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Business
public class UserAdminBusiness {
    private final UserService userService;

    /**
     * [ 사용자 전체 리스트 조회 ]
     * <br/>
     * 관리자가 사용자 전체 리스트를 조회<br/>
     *
     * @return 사용자 전체 리스트
     */
    public Page<UserListResponse> adminUserList(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    /**
     * [ 사용자 권한 변경 ]
     *
     * @param userDto 로그인한 사용자
     * @param userId 변경할 사용자 ID
     * @param request 변경할 권한
     */
    public void updateUser(UserDto userDto, Long userId, UserUpdateUserRoleRequest request) {
        if (userDto.getId().equals(userId)) {
            throw new CoreApiException(ErrorType.BAD_REQUEST, "관리자는 본인의 정보를 수정할 수 없습니다.");
        }

        userService.updateUserRole(userId, request);
    }

    /**
     * [ 사용자 탈퇴 처리 ]
     *
     * @param userDto 로그인한 사용자
     * @param userId 변경할 사용자 ID
     */
    public void deleteUser(UserDto userDto, Long userId) {
        if (userDto.getId().equals(userId)) {
            throw new CoreApiException(ErrorType.BAD_REQUEST, "관리자는 본인의 정보를 수정할 수 없습니다.");
        }

        userService.deleteUser(userId);
    }
}
