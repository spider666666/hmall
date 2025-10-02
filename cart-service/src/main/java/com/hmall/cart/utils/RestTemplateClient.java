package com.hmall.cart.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateClient {

    @Bean
    public RestTemplate RestTemplateMake(){
        return new RestTemplate();
    }
}
