package com.hmall.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
//自定义过滤器的实现

    @Override
//    一个是上下文，相当于全局变量，可以获取请求头等信息，另一个是下一个过滤器
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){

        //1.获取请求
        ServerHttpRequest request = exchange.getRequest();

        //2.打印请求头
        HttpHeaders headers = request.getHeaders();
        System.out.println("headers = " + headers);

        //3.放行
        return chain.filter(exchange);

    }
    @Override
    public int getOrder(){
        return 0;
    }
}
