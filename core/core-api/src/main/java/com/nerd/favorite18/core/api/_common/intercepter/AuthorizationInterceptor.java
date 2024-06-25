package com.nerd.favorite18.core.api._common.intercepter;

import com.nerd.favorite18.core.api._common.support.error.CoreApiException;
import com.nerd.favorite18.core.api._common.support.error.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        final RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();

        if (ObjectUtils.isEmpty(requestContext))  {
            throw new CoreApiException(ErrorType.NULL_POINT);
        }

        final Object userRole = requestContext.getAttribute("userRole", RequestAttributes.SCOPE_REQUEST);

        if (ObjectUtils.isEmpty(userRole)) {
            throw new CoreApiException(ErrorType.NULL_POINT);
        }

        if (!isAdminUser(userRole)) {
            throw new CoreApiException(ErrorType.FORBIDDEN);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean isAdminUser(Object userRole) {

        return userRole.toString().equals("ADMIN");
    }
}
