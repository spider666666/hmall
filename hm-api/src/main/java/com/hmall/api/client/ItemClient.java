package com.hmall.api.client;

import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collection;
import java.util.List;

//防止服务崩溃造成的级联失败
@FeignClient(name = "item-service" , fallbackFactory = ItemClientFallBackFactory.class)
public interface ItemClient {


    //使用restful的调用方式
    @GetMapping("/items")
    List<ItemDTO> getItemsByids(@RequestParam("ids") Collection<Long> ids);

    //扣减库存远程调用
    @PutMapping("/items/stock/deduct")
    void deductStock(@RequestBody List<OrderDetailDTO> detailDTOS);

}
