package com.nerd.favorite18.core.api.jwt.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api.jwt.converter.JwtConverter;
import com.nerd.favorite18.core.api.jwt.dto.JwtRefreshResponse;
import com.nerd.favorite18.core.api.jwt.dto.JwtResponse;
import com.nerd.favorite18.core.api.jwt.model.Token;
import com.nerd.favorite18.core.api.jwt.service.JwtService;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Business
public class JwtBusiness {
    private final JwtService jwtService;
    private final JwtConverter jwtConverter;

    /**
     * [ JWT 발행 ]
     * <br/>
     * 1. 유저정보를 기반으로 액세스 토큰과 리프레쉬 토큰을 생성하여 반환<br/>
     *
     * @param userDto 유저정보
     * @return Jwt 토큰
     */
    public JwtResponse issueToken(UserDto userDto) {
        final Token accessToken = jwtService.issueAccessToken(userDto);
        final Token refreshToken = jwtService.issueRefreshToken(userDto);

        return jwtConverter.toResponse(accessToken, refreshToken);
    }

    /**
     * [ 리프레쉬 토큰 발행 ]
     * <br/>
     * 1. 리프레쉬 토큰 인증 진행<br/>
     * 2. 인증이 완료되면 새로운 액세스토큰 발행<br/>
     *
     * @param refreshToken 리프레쉬 토큰
     * @return 새로운 액세스 토큰
     */
    public JwtRefreshResponse issueRefreshToken(String refreshToken) {
        final UserDto dto = jwtService.validationToken(refreshToken);
        final Token newAccessToken = jwtService.issueAccessToken(dto);

        return jwtConverter.toResponse(newAccessToken);
    }

    /**
     * [토큰 인증 진행]
     * <br/>
     * 1. 액세스토큰 인증 진행<br/>
     *
     * @param accessToken 액세스토큰
     * @return 유저 id
     */
    public UserDto validationAccessToken(String accessToken) {
        final UserDto userDto = jwtService.validationToken(accessToken);

        return userDto;
    }
}
