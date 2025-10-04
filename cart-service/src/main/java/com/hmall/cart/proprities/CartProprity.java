package com.hmall.cart.proprities;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//为了实现配置的热更新而存在，比如说热更新购物车的数量而存在
@Data
@Component
@ConfigurationProperties(prefix = "hm.cart")
public class CartProprity {

    private int maxItem;

}
