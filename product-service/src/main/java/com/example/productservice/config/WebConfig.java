package com.example.productservice.config;

import com.example.productservice.resolvehandler.AuthenticationHolderResolveHandler;
import com.example.productservice.resolvehandler.MemberPrincipalResolveHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthenticationHolderResolveHandler authenticationHolderResolveHandler;
    private final MemberPrincipalResolveHandler memberPrincipalResolveHandler;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationHolderResolveHandler);
        resolvers.add(memberPrincipalResolveHandler);
    }
}
