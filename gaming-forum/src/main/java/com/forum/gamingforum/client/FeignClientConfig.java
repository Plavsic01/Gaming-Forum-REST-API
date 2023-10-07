package com.forum.gamingforum.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public AuthHeaderInterceptor authHeaderInterceptorBean(){
        return new AuthHeaderInterceptor();
    }
}
