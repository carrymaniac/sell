package com.gdou.sell.controller;

import com.gdou.sell.dataobject.ProductCategory;
import com.gdou.sell.dataobject.ProductInfo;
import com.gdou.sell.form.ProductForm;
import com.gdou.sell.service.CategoryService;
import com.gdou.sell.service.ProductService;
import com.gdou.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
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
    CategoryService categoryService;
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

    @RequestMapping("off_sale")
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

    @PostMapping("save")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult,Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        //判断一下id是不是为空，为空说明是新增
        try{
            if(StringUtils.isEmpty(productForm.getProductId())){
                //新增操作,生成新的商品ID
                productForm.setProductId(KeyUtil.genUniqueKey());
            }else {
                //更新操作，找到原有的商品
                productInfo = productService.findOne(productForm.getProductId());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            log.debug("save objevet:{}",productInfo);
            productService.save(productInfo);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }


    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "productId",required = false)String proudctId,Map<String,Object> map){
        if(!StringUtils.isEmpty(proudctId)){
            ProductInfo productInfo = productService.findOne(proudctId);
            map.put("productInfo",productInfo);
        }
        //查询所有的类目
        List<ProductCategory> all = categoryService.findAll();
        map.put("categoryList",all);

        return new ModelAndView(("product/index"),map);
    }
}
