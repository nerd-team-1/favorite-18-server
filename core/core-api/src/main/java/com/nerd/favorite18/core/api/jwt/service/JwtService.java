package com.nerd.favorite18.core.api.jwt.service;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.jwt.helper.TokenHelper;
import com.nerd.favorite18.core.api.jwt.model.Token;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.enums.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final TokenHelper tokenHelper;

    public Token issueAccessToken(UserDto userDto) {
        Map<String, Object> data = getClaimData(userDto);

        return tokenHelper.issueAccessToken(data);
    }

    public Token issueRefreshToken(UserDto userDto) {
        Map<String, Object> data = getClaimData(userDto);

        return tokenHelper.issueRefreshToken(data);
    }

    public UserDto validationToken(String token) {
        final Map<String, Object> map = tokenHelper.validationTokenWithThrow(token);
        final Object userId = map.get("userId");
        final Object userRole = map.get("userRole");

        Objects.requireNonNull(userId, () -> { throw new CoreApiException(ErrorType.NULL_POINT); });
        Objects.requireNonNull(userRole, () -> { throw new CoreApiException(ErrorType.NULL_POINT); });

        return UserDto.of(
                Long.parseLong(userId.toString()),
                UserRole.valueOf(userRole.toString())
        );
    }

    private Map<String, Object> getClaimData(UserDto userDto) {

        return Map.of(
                "userId", userDto.getId(),
                "userRole", userDto.getRole()
        );
    }
}
