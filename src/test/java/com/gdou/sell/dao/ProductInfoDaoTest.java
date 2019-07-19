package com.gdou.sell.dao;

import com.gdou.sell.dataobject.ProductInfo;
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
public class ProductInfoDaoTest {

    @Autowired
    private ProductInfoDao productInfoDao;
    @Test
    public void findByProductStatus() {
        List<ProductInfo> byProductStatus = productInfoDao.findByProductStatus(0);
        Assert.assertEquals(1,byProductStatus.size());
    }

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("台湾鸡排");
        productInfo.setProductPrice(new BigDecimal(13.5));
        productInfo.setProductDescription("台湾炸老母鸡鸡排");
        productInfo.setProductStock(100);
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(1);
        productInfo.setProductIcon("http://xxxx.com/2.jpg");
        productInfo.setProductId("66666");
        ProductInfo save = productInfoDao.save(productInfo);
        Assert.assertNotNull(save);
    }
}