package com.hmall.cart;

import com.hmall.api.config.FeignLogConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.hmall.api.client")
//启动openfeign,并且指定扫描包,并使用全局配置defaultConfiguration = FeignLogConfiguration.class（这里的配置类没有交给spring管理，所以直接作为参数传入了）

public class CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }

}
