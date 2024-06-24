package com.nerd.favorite18.core.api._common.resolver;

import com.nerd.favorite18.core.api._common.annotation.UserSession;
import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import com.nerd.favorite18.core.api.user.dto.UserDto;
import com.nerd.favorite18.core.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class UserSessionResolver implements HandlerMethodArgumentResolver {
    private final UserService userService;

    /**
     * [ 유저세션 리졸버 ]
     * <br />
     * WebConfig 에 지정된 주소로 요청시, UserSession 어노테이션을 확인하고 인터셉터에서 전달받은 유저정보를 각 컨트롤러에 전달합니다. <br />
     *
     * @param parameter the method parameter to check
     * @return 확인 값
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean annotation = parameter.hasParameterAnnotation(UserSession.class);
        boolean parameterType = parameter.getParameterType().equals(UserDto.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();

        if (ObjectUtils.isEmpty(requestContext)) {
            throw new CoreApiException(ErrorType.NULL_POINT);
        }

        final Object userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        if (ObjectUtils.isEmpty(userId)) {
            throw new CoreApiException(ErrorType.NULL_POINT);
        }

        final UserDto userDto = userService.getUserActiveWithThrow(Long.parseLong(userId.toString()));

        // 사용자 정보 세팅
        return userDto;
    }
}
