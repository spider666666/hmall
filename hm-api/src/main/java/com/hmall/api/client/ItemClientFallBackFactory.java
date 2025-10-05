package com.hmall.api.client;

import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import java.util.Collection;
import java.util.List;

@Slf4j
public class ItemClientFallBackFactory implements FallbackFactory<ItemClient> {
    //实现fallback，防止产生服务级联崩溃问题
    @Override
    public ItemClient create(Throwable cause){
        return new ItemClient() {
            @Override
            public List<ItemDTO> getItemsByids(Collection<Long> ids) {
                log.info("查询购物车失败，系统繁忙，请稍后再试",cause);
                return null;
            }

            @Override
            public void deductStock(List<OrderDetailDTO> detailDTOS) {
                log.info("扣减库存失败",cause);
                //不清楚会抛出什么异常，交给调用者去管理
                throw new RuntimeException(cause);

            }
        };
    }
}
