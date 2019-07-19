package com.gdou.sell.service.impl;

import com.gdou.sell.Exception.SellException;
import com.gdou.sell.dto.OrderDTO;
import com.gdou.sell.enums.ResultEnum;
import com.gdou.sell.service.BuyerService;
import com.gdou.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    //
    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if(orderDTO == null){
            log.error("[取消订单]查询不到该订单，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid,String orderId){
        OrderDTO one = orderService.findOne(orderId);
        if(one == null){
            log.error("[查询订单]查询不到该订单，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(!one.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[查询订单] 订单的用户ID与openid不一致，openid={},orderDTO={}",openid,one);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return one;
    }
}
