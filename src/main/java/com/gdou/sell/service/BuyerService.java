package com.gdou.sell.service;

import com.gdou.sell.dto.OrderDTO;

public interface BuyerService {
    /**
     * 查询一个订单
     * @param openid 用户的openId
     * @param orderId 用户的订单ID
     * @return
     */
    OrderDTO findOrderOne(String openid,String orderId);

    /**
     * 取消一个订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO cancelOrder(String openid,String orderId);

}
