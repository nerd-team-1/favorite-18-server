package com.nerd.favorite18.core.api.auth.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api._common.client.OAuth2Client;
import com.nerd.favorite18.core.api.auth.dto.request.AuthRefreshRequest;
import com.nerd.favorite18.core.api.auth.dto.response.AuthGoogleAccessTokenResponse;
import com.nerd.favorite18.core.api.auth.model.GoogleUserInfo;
import com.nerd.favorite18.core.api.jwt.business.JwtBusiness;
import com.nerd.favorite18.core.api.jwt.dto.JwtRefreshResponse;
import com.nerd.favorite18.core.api.jwt.dto.JwtResponse;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.dto.request.UserRegisterRequest;
import com.nerd.favorite18.core.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Business
public class AuthBusiness {
    private final JwtBusiness jwtBusiness;
    private final UserService userService;

    private final OAuth2Client oauth2Client;

    /**
     * [ 로그인 요청 ]
     * <br/>
     * 1. 구글 OAuth2.0 로그인 요청 클라이언트 실행 <br/>
     *
     * @return Redirect URI
     */
    public URI login() {
        return oauth2Client.getAuthorizationUri();
    }

    /**
     * [ 콜백 로그인 ]
     * <br/>
     * 1. OAuth2.0 로그인 진행 후 리다이렉트 되어 실행되는 로직 <br/>
     * 2. 구글 API 에서 AccessToken 받음 <br/>
     * 3. 전달받은 AccessToken 으로 유저정보 API 실행 <br/>
     * 4. 구글 유저정보 기반으로 DB 에서 유저 조회 <br/>
     * 5. 유저 없으면 회원가입 진행 후 유저반환 <br/>
     * 6. 유저 있으면 바로 유저 조회결과 반환 <br/>
     * 7. 반환된 유저정보 기준으로 Jwt 토큰 발행 <br/>
     *
     * @param authorizationCode 구글로 부터 전달받은 인증코드
     * @return JwtResponse JWT 토큰 발행
     */
    public JwtResponse callbackGoogle(String authorizationCode) {
        AuthGoogleAccessTokenResponse tokenResponse = oauth2Client.getAccessToken(authorizationCode);

        String accessToken = tokenResponse.getAccessToken();
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oauth2Client.getUserInfo(accessToken));

        // 구글 유저 정보 기반으로 유저를 조회
        UserDto userDto;
        try {
            userDto = userService.getUserWithThrow(googleUserInfo.getSubId(), googleUserInfo.getEmail());
        } catch (RuntimeException e) {
            // 조회되지 않으면 강제 회원가입 후 유저 반환
            userDto = userService.saveUser(UserRegisterRequest.of(
                    googleUserInfo.getSubId(), googleUserInfo.getEmail(), googleUserInfo.getName(), googleUserInfo.getThumbnail())
            );
        }

        // Jwt 토큰 발행
        return jwtBusiness.issueToken(userDto);
    }

    /**
     * [ 리프레쉬 토큰 발행 ]
     * <br/>
     * 1. 클라이언트로 부터 전달받은 리프레쉬 토큰을 기준으로 새로운 액세스 토큰을 반환 <br/>
     *
     * @param request 리프레쉬 토큰
     * @return 새로운 액세스 토큰
     */
    public JwtRefreshResponse refreshToken(AuthRefreshRequest request) {
        final JwtRefreshResponse jwtRefreshResponse = jwtBusiness.issueRefreshToken(request.getRefreshToken());

        return jwtRefreshResponse;
    }
}
