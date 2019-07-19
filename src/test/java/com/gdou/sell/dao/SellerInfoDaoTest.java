package com.gdou.sell.dao;

import com.gdou.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author Ha
 * @DATE 2019/6/5 20:04
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {

    @Autowired
    SellerInfoDao sellerInfoDao;
    @Test
    public void findByUserNameAndPassword() {
        SellerInfo save = sellerInfoDao.findByUsernameAndPassword("admin","123456");
        Assert.assertNotNull(save);
    }
}