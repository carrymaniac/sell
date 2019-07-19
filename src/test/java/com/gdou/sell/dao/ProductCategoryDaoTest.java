package com.gdou.sell.dao;

import com.gdou.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    ProductCategoryDao productCategoryDao ;

    @Test
    public void findOneTest(){
        Optional<ProductCategory> one = productCategoryDao.findById(1);
        if(one.isPresent()){
            ProductCategory productCategory = one.get();
            System.out.println(productCategory.toString());
        }
    }

    /**
     * 自动帮你回滚
     */
    @Test
//    @Transactional
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱" );
        productCategory.setCategoryType(2);
        productCategoryDao.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<ProductCategory> byCategoryTypeIn = productCategoryDao.findByCategoryTypeIn(Arrays.asList(1, 2));
        Assert.assertNotEquals(null,byCategoryTypeIn);
    }

}