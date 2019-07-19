package com.gdou.sell.dao;

import com.gdou.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {
    @Autowired
    OrderDetailDao orderDetailDao;
    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123457");
        orderDetail.setOrderId("abc112");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductName("白粥2019");
        orderDetail.setProductId("123459");
        orderDetail.setProductPrice(new BigDecimal(3.2));
        orderDetail.setProductQuantity(2);
        OrderDetail save = orderDetailDao.save(orderDetail);
        Assert.assertNotNull(save);

    }
    @Test
    public void findByOrOrderId() {
        List<OrderDetail> byOrOrderId = orderDetailDao.findByOrOrderId("123456");
        Assert.assertNotNull(byOrOrderId);
    }
}