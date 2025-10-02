package com.hmall.api.client;

import com.hmall.api.dto.ItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient(name = "item-service")
public interface ItemClient {


    //使用restful的调用方式
    @GetMapping("/items")
    List<ItemDTO> getItemsByids(@RequestParam("ids") Collection<Long> ids);

}
