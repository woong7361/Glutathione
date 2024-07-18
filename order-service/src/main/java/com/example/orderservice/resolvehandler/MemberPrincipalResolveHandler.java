package com.example.orderservice.resolvehandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;
import java.util.Map;

/**
 * AuthenticationHolder 에서 인증객체를 가져오는 역할을 한다.
 */
@Component
public class MemberPrincipalResolveHandler implements HandlerMethodArgumentResolver {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_ = "Bearer ";

    /**
     * parameter가 Principal.class 인지 AND @Annotation이 AuthenticationPrincipal.class 인지
     * @param parameter method Argument parameter
     * @return 파라미터를 지원하는지
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return isSupportAnnotationClass(parameter) & isSupportParameterType(parameter);
    }

    /**
     * Authentication Holder에서 인증된 회원 객체를 꺼내준다.
     * @return 인증된 회원 객체
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader(AUTHORIZATION);

        if (! StringUtils.hasText(token)) {
            return new Principal(-1L);
        }
        token = token.replace(BEARER_, "");

        String claim = token.split("[.]")[1];
        byte[] decode = Base64.getDecoder().decode(claim);
        String decodedString = new String(decode);
        Map<String, String> map = new ObjectMapper().readValue(decodedString, Map.class);


        return new Principal(Long.valueOf(map.get("sub")));
    }

    private boolean isSupportParameterType(MethodParameter parameter) {
        return Principal.class.isAssignableFrom(parameter.getParameterType());
    }

    private boolean isSupportAnnotationClass(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberPrincipal.class);
    }

}
