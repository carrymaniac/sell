package com.gdou.sell.service.impl;

import com.gdou.sell.dataobject.ProductInfo;
import com.gdou.sell.enums.ProductStatusEnum;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;
    @Test
    public void findOne() {
        ProductInfo one = productService.findOne("123456");
        Assert.assertNotNull(one);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productService.findUpAll();
        Assert.assertNotEquals(0,upAll.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<ProductInfo> all = productService.findAll(pageRequest);
        Assert.assertNotNull(all);
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("番薯肉");
        productInfo.setProductPrice(new BigDecimal(4.2));
        productInfo.setProductDescription("番薯做成的肉");
        productInfo.setProductStock(100);
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(2);
        productInfo.setProductIcon("http://xxxx.com/1.jpg");
        productInfo.setProductId("123457");
        productService.save(productInfo);
    }

    @Test
    public void sale(){
        ProductInfo productInfo = productService.offSale("123456");
        Assert.assertNotNull(productInfo);

    }

}