package com.nerd.favorite18.core.api.auth.business;

import com.nerd.favorite18.core.api._common.annotation.Business;
import com.nerd.favorite18.core.api._common.client.OAuth2Client;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api._common.utils.SHA256HashUtils;
import com.nerd.favorite18.core.api.auth.dto.request.AuthLoginGoogleRequest;
import com.nerd.favorite18.core.api.auth.dto.request.AuthRefreshRequest;
import com.nerd.favorite18.core.api.auth.dto.request.AuthSignupGoogleRequest;
import com.nerd.favorite18.core.api.auth.dto.response.AuthGoogleAccessTokenResponse;
import com.nerd.favorite18.core.api.auth.model.GoogleUserInfo;
import com.nerd.favorite18.core.api.auth.model.GoogleUserInfoMobile;
import com.nerd.favorite18.core.api.auth.model.OAuth2UserInfo;
import com.nerd.favorite18.core.api.jwt.business.JwtBusiness;
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
     * [ 회원가입 ]
     * <br/>
     * 1. OAuth2.0 로그인 진행 후 리다이렉트 되어 실행되는 로직 <br/>
     *
     * @param request 생년월일, 성별, 사용자 정보
     * @return 사용자 정보
     */
    public UserDto signUp(AuthSignupGoogleRequest request) {
        GoogleUserInfoMobile googleUserInfo = new GoogleUserInfoMobile(request.getUserInfo());

        return userService.saveUser(
                UserRegisterRequest.of(
                        googleUserInfo.getSubId(),
                        googleUserInfo.getEmail(),
                        googleUserInfo.getName(),
                        request.getBirth(),
                        request.getGender(),
                        googleUserInfo.getThumbnail()
                )
        );
    }

    /**
     * [ 로그인 체크 With Error ]
     * <br/>
     * 1. 구글 유저정보 기반으로 DB 에서 유저 조회 <br/>
     * 2. 유저 없으면 400 에러 반환 <br/>
     * 3. 유저 있으면 바로 유저 조회 결과 반환 <br/>
     * 4. 반환된 유저정보 기준으로 Jwt 토큰 발행 <br/>
     *
     * @param userInfo OAuth 유저정보
     * @return JwtResponse JWT 토큰 발행
     */
    private JwtResponse checkLoginUserWithError(OAuth2UserInfo userInfo) {
        UserDto userDto;
        try {
            // 유저 조회하고 있으면 바로 반환, 만약 유저 상태가 ACTIVE 가 아니면 ACTIVE 로 바꾸고 사용자 반환
            userDto = userService.getUserAndUpdateStatusWithThrow(userInfo.getSubId(), userInfo.getEmail());
        } catch (RuntimeException e) {
            throw new CoreApiException(ErrorType.USER_NOT_FOUND, "회원가입이 되어있지 않습니다.");
        }

        final JwtResponse jwtResponse = jwtBusiness.issueToken(userDto);
        userService.updateUserRefreshToken(userDto.getId(), SHA256HashUtils.hashToken(jwtResponse.getRefreshToken()));

        return jwtResponse;
    }

    /**
     * [ 모바일 로그인 요청 ]
     * <br/>
     * 모바일에서 확인한 유저정보 기반으로 로그인 진행 <br/>
     *
     * @param request idToken, userInfo
     * @return JwtResponse JWT 토큰 발행
     */
    public JwtResponse loginGoogle(AuthLoginGoogleRequest request) {
        GoogleUserInfoMobile googleUserInfo = new GoogleUserInfoMobile(request.getUserInfo());

        return checkLoginUserWithError(googleUserInfo);
    }

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
     * [ 로그인 체크 ]
     * <br/>
     * 1. 구글 유저정보 기반으로 DB 에서 유저 조회 <br/>
     * 2. 유저 없으면 회원가입 진행 후 유저반환 <br/>
     * 3. 유저 있으면 바로 유저 조회결과 반환 <br/>
     * 4. 반환된 유저정보 기준으로 Jwt 토큰 발행 <br/>
     *
     * @param userInfo OAuth 유저정보
     * @return JwtResponse JWT 토큰 발행
     */
    private JwtResponse checkLoginUser(OAuth2UserInfo userInfo) {
        UserDto userDto;
        try {
            // 유저 조회하고 있으면 바로 반환, 만약 유저 상태가 ACTIVE 가 아니면 ACTIVE 로 바꾸고 사용자 반환
            userDto = userService.getUserAndUpdateStatusWithThrow(userInfo.getSubId(), userInfo.getEmail());
        } catch (RuntimeException e) {
            // 조회되지 않으면 강제 회원가입 후 유저 반환
            userDto = userService.saveUser(UserRegisterRequest.of(
                    userInfo.getSubId(),
                    userInfo.getEmail(),
                    userInfo.getName(),
                    null,
                    null,
                    userInfo.getThumbnail())
            );
        }

        final JwtResponse jwtResponse = jwtBusiness.issueToken(userDto);
        userService.updateUserRefreshToken(userDto.getId(), SHA256HashUtils.hashToken(jwtResponse.getRefreshToken()));

        return jwtResponse;
    }

    /**
     * [ 웹 콜백 로그인 ]
     * <br/>
     * 1. OAuth2.0 로그인 진행 후 리다이렉트 되어 실행되는 로직 <br/>
     * 2. 구글 API 에서 AccessToken 받음 <br/>
     * 3. 전달받은 AccessToken 으로 유저정보 API 실행 <br/>
     * 4. 유저정보로 유저 체크 후 JWT 토큰 발행 <br/>
     *
     * @param authorizationCode 구글로 부터 전달받은 인증코드
     * @return JwtResponse JWT 토큰 발행
     */
    public JwtResponse callbackGoogle(String authorizationCode) {
        AuthGoogleAccessTokenResponse tokenResponse = oauth2Client.getAccessToken(authorizationCode);

        String accessToken = tokenResponse.getAccessToken();
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oauth2Client.getUserInfo(accessToken));

        return checkLoginUser(googleUserInfo);
    }

    /**
     * [ 리프레쉬 토큰 발행 ]
     * <br/>
     * 1. 클라이언트로 부터 전달받은 리프레쉬 토큰을 기준으로 새로운 액세스 토큰을 반환 <br/>
     *
     * @param request 리프레쉬 토큰
     * @return 새로운 액세스 토큰
     */
    public JwtResponse refreshToken(AuthRefreshRequest request) {
        final JwtResponse jwtResponse = jwtBusiness.issueRefreshToken(request);

        return jwtResponse;
    }

    public void logout(UserDto user) {
        userService.updateUserRefreshToken(user.getId(), null);
    }
}
