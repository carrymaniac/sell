package com.gdou.sell.controller;

import com.gdou.sell.VO.ProductInfoVO;
import com.gdou.sell.VO.ProductVo;
import com.gdou.sell.VO.ResultVO;
import com.gdou.sell.dataobject.ProductCategory;
import com.gdou.sell.dataobject.ProductInfo;
import com.gdou.sell.service.CategoryService;
import com.gdou.sell.service.ProductService;
import com.gdou.sell.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    ProductService productService ;
    @Autowired
    CategoryService categoryService;
    @GetMapping(value = "/list")
    public ResultVO list(){
        //1.查询所有的上架商品
        List<ProductInfo> ProductInfoList = productService.findUpAll();

        //2.查询需要的类目(一次性查询,减少sql压力)
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for (ProductInfo productInfo:upAll){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }

        //使用了java8的stream和lamda
        List<Integer> collect = ProductInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> ProductCategoryList = categoryService.findByCategoryTypeIn(collect);

        //3.数据封装,数据库查询不要放在for循环中
        List<ProductVo> productVos = new ArrayList<>();
        for(ProductCategory productCategory : ProductCategoryList){
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategorytype(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOS = new ArrayList<>();
            for(ProductInfo productInfo : ProductInfoList){
                if(productInfo.getCategoryType() == productCategory.getCategoryType()){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOS.add(productInfoVO);
                }
            }
            productVo.setProductInfoVOList(productInfoVOS);
            productVos.add(productVo);
        }
        return ResultVOUtil.success(productVos);
    }

}
