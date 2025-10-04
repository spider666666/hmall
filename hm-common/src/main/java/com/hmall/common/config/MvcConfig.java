package com.hmall.common.config;

import com.hmall.common.interceptor.UserInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//仍然存在问题，由于网关底层不是有mvc实现的，所以不能装配mvc相关的配置类，所以要进行排除
@ConditionalOnClass(DispatcherServlet.class)
//dispatcherServlet是springMvc的核心类，所以只有在该模块中存在该类时才能装配该配置类
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里就不必要设置拦截路径了，因为所有的请求都可以被放行，只是添加了一个请求头的属性
        registry.addInterceptor(new UserInterceptor());
    }

}
