package com.nerd.favorite18.core.api.user.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.user.converter.UserConverter;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserRegisterRequest;
import com.nerd.favorite18.core.api.user.dto.request.UserUpdateNicknameRequest;
import com.nerd.favorite18.core.enums.user.UserStatus;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import com.nerd.favorite18.storage.db.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    /**
     * 상태 관계없이 사용자 1명 조회 후
     * 상태가 Active 가 아니면 Active 로 변경
     *
     * @param subId Oauth ID
     * @param email 사용자 이메일
     * @return 사용자 DTO
     */
    @Transactional
    public UserDto getUserAndUpdateStatusWithThrow(String subId, String email) {
        User userEntity = Optional.ofNullable(
                userRepository.findFirstBySubIdAndEmailOrderByIdDesc(subId, email)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        if (userEntity.getStatus() != UserStatus.ACTIVE) {
            userEntity.updateStatus(UserStatus.ACTIVE);

            userEntity = userRepository.save(userEntity);
        }

        return userConverter.toDto(userEntity);
    }

    /**
     * 사용자 ID 로 Active 상태 사용자 1명 조회
     *
     * @param userId 사용자 ID
     * @return 사용자 DTO
     */
    @Transactional(readOnly = true)
    public UserDto getUserActiveWithThrow(Long userId) {
        User userEntity = Optional.ofNullable(
                userRepository.findFirstByIdAndStatusOrderByIdDesc(userId, UserStatus.ACTIVE)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        return userConverter.toDto(userEntity);
    }

    /**
     * 사용자 정보 저장
     *
     * @param request 사용자 가입 정보
     * @return 사용자 DTO
     */
    @Transactional
    public UserDto saveUser(UserRegisterRequest request) {
        final User userEntity = userConverter.toEntity(request);

        final User saveEntity = userRepository.save(userEntity);

        return userConverter.toDto(saveEntity);
    }

    /**
     * 사용자 조회 후 사용자 닉네임 업데이트하여 저장
     *
     * @param user 로그인 사용자 정보
     * @param request 변경할 닉네임
     */
    @Transactional
    public void updateNickname(UserDto user, UserUpdateNicknameRequest request) {
        User userEntity = Optional.ofNullable(
                userRepository.findFirstByIdAndStatusOrderByIdDesc(user.getId(), UserStatus.ACTIVE)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        userEntity.updateNickname(request.getNickname());

        userRepository.save(userEntity);
    }

    /**
     * ACTIVE 상태 사용자 조회하여 DELETE 로 변경 후 저장
     *
     * @param user 로그인 사용자 정보
     */
    @Transactional
    public void deleteUser(UserDto user) {
        User userEntity = Optional.ofNullable(
                userRepository.findFirstByIdAndStatusOrderByIdDesc(user.getId(), UserStatus.ACTIVE)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        userEntity.updateStatus(UserStatus.DELETE);

        userRepository.save(userEntity);
    }
}
