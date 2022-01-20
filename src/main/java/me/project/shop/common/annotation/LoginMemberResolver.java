package me.project.shop.common.annotation;

import lombok.RequiredArgsConstructor;
import me.project.shop.common.exception.BusinessException;
import me.project.shop.common.exception.BusinessMessage;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginMemberResolver implements HandlerMethodArgumentResolver {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String sessionId = webRequest.getSessionId();
        String email = redisTemplate.opsForValue().get(sessionId);

        if (email == null) throw new BusinessException(BusinessMessage.NOT_LOGGED_IN);
        return email;
    }
}
