package com.hmall.api.config;


import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

public class defaultFeignConfiguration {

//    @Bean
//    public Logger.Level feignLevel(){
//        //返回所有的日志信息
//        return Logger.Level.FULL;
//    }

    //在进行微服务之间的通信之前时候加上请求头，记录用户信息,
    @Bean
    public RequestInterceptor UserInfoRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {

                Long userInfo = UserContext.getUser();
                if (userInfo!=null){
                    requestTemplate.header("user-info", String.valueOf(userInfo));
                }
            }
        };
    }
}
