package com.gdou.sell.service.impl;

import com.gdou.sell.dataobject.OrderDetail;
import com.gdou.sell.dto.OrderDTO;
import com.gdou.sell.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    OrderServiceImpl orderService;

    private final String OPENID = "110110";
    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("gdou");
        orderDTO.setBuyerName("番薯");
        orderDTO.setBuyerPhone("135646468");
        orderDTO.setBuyerOpenid(OPENID);
        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductName("白粥");
        orderDetail.setProductQuantity(1);
        orderDetails.add(orderDetail);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("66666");
        o2.setProductName("台湾鸡排");
        o2.setProductQuantity(3);
        orderDetails.add(o2);

        orderDTO.setOrderDetailList(orderDetails);
        OrderDTO result = orderService.create(orderDTO);
        log.info("{创建订单}:"+result);
    }

    @Test
    public void findOne() {
        OrderDTO one = orderService.findOne("1557563079903933981");
        Assert.assertNotNull(one);
        System.out.println(one);
    }

    @Test
    public void findList() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderDTO> list = orderService.findList("110110", pageRequest);
        List<OrderDTO> content = list.getContent();
        Assert.assertNotNull(content);
    }

    @Test
    public void cancel() {
    }

    @Test
    public void paid() {
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("1557563079903933981");
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(),result.getOrderStatus());
    }

    @Test
    public void List(){
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<OrderDTO> list = orderService.findList(pageRequest);
        List<OrderDTO> content = list.getContent();
        Assert.assertNotNull(content);
    }
}