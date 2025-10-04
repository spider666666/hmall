package com.hmall.gateway.filters;

import com.hmall.common.exception.UnauthorizedException;
import com.hmall.gateway.proprities.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {


    private final AuthProperties authProperties;

    private final JwtTool jwtTool;

    //该bean没有交给spring自动管理，只能手动构建出来
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    //该全局过滤器用于登入的校验功能
    @Override
    public Mono<Void> filter(ServerWebExchange exchange , GatewayFilterChain chain) {

        //1.首先请求对象
        ServerHttpRequest request = exchange.getRequest();

        //2.接着获取请求头对象
        HttpHeaders headers = request.getHeaders();

        //3.判断当前路径是否需要机型登入校验
        if(isexclude(request.getPath().toString())){
            return chain.filter(exchange);
        }
        //4.获取请求头的token
        List<String> authorization = headers.get("Authorization");


        //4.判断token是否存在
        String token = null;
        if (authorization.size() != 0 || !authorization.isEmpty()){
            token = authorization.get(0);
        }

        //4，对token进行解析，获取用户信息
        //4.1如果token存在
        Long userId = null;
        try{
            userId = jwtTool.parseToken(token);
        }
        catch (UnauthorizedException e){
            //进行异常的处理逻辑
            //1。获取响应头
            ServerHttpResponse response = exchange.getResponse();
            //2.对相应头进行处理401
            response.setRawStatusCode(401);
            //3.返回相应头
            return response.setComplete();
        }
        //TODO  对获取得到的用户信息继续处理
        //4.2将获取到的用户信息通过网关传递到微服务中去,之后微服务去做拦截获取用户信息就可以了
        //mutate是突变的意思，可以修改下游的上下文
        String userInfo = userId.toString();
        //在请求头中放置属性值的时候，最好是直接放入，不做转换
        ServerWebExchange ex = exchange.mutate()
                .request(builder -> builder.header("user-info", userInfo))
                .build();

        //5.最后进行放行操作
        return chain.filter(ex);
    }

    private boolean isexclude(String path) {
        //使用循环判断当前是否在排除的路径当中
        //首先获取当前路径
        for (String excludePath : authProperties.getExcludePaths()) {
            if(antPathMatcher.match(excludePath,path)){
                return true;
            }
        }
        //如果不匹配，直接返回错误；
        return false;
    }


    @Override
    public int getOrder(){
        return 0;
    }
}
