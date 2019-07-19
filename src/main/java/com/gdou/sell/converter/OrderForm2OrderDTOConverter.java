package com.gdou.sell.converter;

import com.gdou.sell.Exception.SellException;
import com.gdou.sell.dataobject.OrderDetail;
import com.gdou.sell.dto.OrderDTO;
import com.gdou.sell.enums.ResultEnum;
import com.gdou.sell.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetails = new ArrayList<>();
        try{
            orderDetails = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){
            }.getType());
        }catch (Exception e){
            log.error("[对象转换]错误，String={}",orderForm.getItems());
            throw  new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }
}
