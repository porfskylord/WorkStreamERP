package com.wserp.authservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceFeignConfig implements RequestInterceptor {

    @Value("${internal.token}")
    private String internalToken;

    @Override
    public void apply(RequestTemplate template) {
        template.header("X-INTERNAL-TOKEN", internalToken);
    }
}