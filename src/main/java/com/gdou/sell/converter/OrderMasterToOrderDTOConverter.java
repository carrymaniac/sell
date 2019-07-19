package com.gdou.sell.converter;

import com.gdou.sell.dataobject.OrderMaster;
import com.gdou.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMasterToOrderDTOConverter {
    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> convert(List<OrderMaster> orderMasters){
        List<OrderDTO> collect = orderMasters.stream().map(e -> convert(e)).collect(Collectors.toList());
        return collect;
    }
}
