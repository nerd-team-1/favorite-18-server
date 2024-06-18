package com.nerd.favorite18.core.api._common.intercepter;

import com.nerd.favorite18.core.api.jwt.business.JwtBusiness;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final JwtBusiness jwtBusiness;

    /**
     * [ 로그인 인증 인터셉터 ]
     * <br/>
     * WebConfig 에 지정된 주소로 요청시, 헤더를 확인하여 Jwt 인증여부를 확인하는 인터셉터입니다.<br/>
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return 인터셉터 확인 값
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        final String accessToken = request.getHeader("authorization-token");
        if (accessToken == null) {
            throw new CoreApiException(ErrorType.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        Long userId = jwtBusiness.validationAccessToken(accessToken);

        if(userId != null) {
            final RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);

            return true;
        }

        throw new CoreApiException(ErrorType.BAD_REQUEST);
    }
}
