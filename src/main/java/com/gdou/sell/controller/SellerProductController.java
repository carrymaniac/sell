package com.gdou.sell.controller;

import com.gdou.sell.dataobject.ProductInfo;
import com.gdou.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Map;

/**
 * @Author Ha
 * @DATE 2019/6/25 19:45
 **/
@Controller
@RequestMapping("seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    ProductService productService;
    /**
     * 列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<ProductInfo> ProductInfoPage = productService.findAll(pageRequest);
        List<ProductInfo> content = ProductInfoPage.getContent();
        for (ProductInfo productInfo:content
             ) {
            log.debug("状态为: {}",productInfo.getProductStatusEnum().getMsg());
        }
        map.put("ProductInfoPage",ProductInfoPage);
        map.put("currentPage",page);
        map.put("pageSize",size);
        return new ModelAndView("product/list",map);
    }

    @RequestMapping("on_sale")
    public ModelAndView onSale(@RequestParam(value = "productId")String productInfoId,Map<String,Object> map){
        try {
            productService.onSale(productInfoId);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @RequestMapping("on_sale")
    public ModelAndView offSale(@RequestParam(value = "productId")String productInfoId,Map<String,Object> map){
        try {
            productService.offSale(productInfoId);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
