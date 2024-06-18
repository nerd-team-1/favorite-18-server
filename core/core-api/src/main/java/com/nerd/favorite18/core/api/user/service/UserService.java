package com.nerd.favorite18.core.api.user.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.user.converter.UserConverter;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserRegisterRequest;
import com.nerd.favorite18.core.api.user.dto.request.UserUpdateNicknameRequest;
import com.nerd.favorite18.storage.db.core.user.entity.User;
import com.nerd.favorite18.storage.db.core.user.repository.UserRepository;
import com.nerd.favorite18.storage.db.core.user.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional(readOnly = true)
    public UserDto getUserWithThrow(String subId, String email) {
        User userEntity = Optional.ofNullable(
                userRepository.findBySubIdAndEmailAndStatusOrderByIdDesc(subId, email, UserStatus.ACTIVE)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        return userConverter.toDto(userEntity);
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithThrow(Long userId) {
        User userEntity = Optional.ofNullable(
                userRepository.findFirstByIdAndStatusOrderByIdDesc(userId, UserStatus.ACTIVE)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        return userConverter.toDto(userEntity);
    }

    @Transactional
    public UserDto saveUser(UserRegisterRequest request) {
        final User userEntity = userConverter.toEntity(request);

        final User newUserEntity = userRepository.save(userEntity);

        return userConverter.toDto(newUserEntity);
    }

    @Transactional
    public void updateNickname(UserDto user, UserUpdateNicknameRequest request) {
        User userEntity = Optional.ofNullable(
                userRepository.findFirstByIdAndStatusOrderByIdDesc(user.getId(), UserStatus.ACTIVE)
        ).orElseThrow(() -> new CoreApiException(ErrorType.USER_NOT_FOUND));

        userEntity.updateNickname(request.getNickname());

        userRepository.save(userEntity);
    }
}
