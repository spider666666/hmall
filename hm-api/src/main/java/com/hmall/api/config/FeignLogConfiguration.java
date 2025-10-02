package com.hmall.api.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FeignLogConfiguration {

    @Bean
    public Logger.Level feignLevel(){
        //返回所有的日志信息
        return Logger.Level.FULL;
    }
}
