package com.gdou.sell.service.impl;

import com.gdou.sell.dataobject.SellerInfo;
import com.gdou.sell.service.SellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author Ha
 * @DATE 2019/6/5 20:42
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class SellerIServiceTest {

    @Autowired
    SellerServiceImpl sellerService;
    @Test
    public void findByUsernameAndPassword() {
        SellerInfo admin = sellerService.findByUsernameAndPassword("admin", "123456");
        Assert.assertNotNull(admin);
    }

    @Test
    public void findByUsername() {
        SellerInfo admin = sellerService.findByUsername("admin");
        Assert.assertNotNull(admin);
    }
}