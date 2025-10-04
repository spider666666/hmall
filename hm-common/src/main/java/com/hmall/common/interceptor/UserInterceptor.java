package com.hmall.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.hmall.common.utils.UserContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取请求头//2，获取用户信息
        String userInfo = request.getHeader("user-info");

        //（判断是否存在）
        if (!StrUtil.isNotBlank(userInfo)){
            //3.将用户信息存储到当前的threadlocal中
            UserContext.setUser(Long.valueOf(userInfo));
        }
        //4.放行
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    //当请求发送完之后，把threadlocal中的数据清除
        Long userInfo = UserContext.getUser();
        UserContext.removeUser();

    }
}
