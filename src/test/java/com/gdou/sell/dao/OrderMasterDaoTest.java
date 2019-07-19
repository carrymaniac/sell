package com.gdou.sell.dao;

import com.gdou.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    OrderMasterDao orderMasterDao;
    @Test
    public void findByBuyerOpenid() {
        Page<OrderMaster> abc111 = orderMasterDao.findByBuyerOpenid("110110", PageRequest.of(0, 1));
        abc111.getTotalElements();
        abc111.getTotalPages();
        List<OrderMaster> content = abc111.getContent();
        Assert.assertNull(content);
    }

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerName("番薯");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setOrderId("abc111");
        orderMaster.setBuyerAddress("广东海洋大学");
        orderMaster.setBuyerPhone("1356558695");
        orderMaster.setOrderAmount(new BigDecimal(2.3));
        OrderMaster save = orderMasterDao.save(orderMaster);
        Assert.assertNotNull(save);
    }

}