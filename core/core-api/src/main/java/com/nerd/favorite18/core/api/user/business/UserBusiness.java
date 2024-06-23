package com.nerd.favorite18.core.api.user.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserUpdateNicknameRequest;
import com.nerd.favorite18.core.api.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Business
public class UserBusiness {
    private final UserService userService;

    /**
     * [ 닉네임 수정 ]
     *
     * @param user 로그인 유저 정보
     * @param request 업데이트할 이름
     */
    public void updateNickname(UserDto user, UserUpdateNicknameRequest request) {
        userService.updateNickname(user, request);
    }

    /**
     * [ 회원 탈퇴 ]
     * <br/>
     * 유저 정보를 삭제하지 않고, 상태만 DELETE 로 변경 <br/>
     *
     * @param user 로그인 유저 정보
     */
    public void deleteUser(UserDto user) {
        userService.deleteUser(user);
    }
}
