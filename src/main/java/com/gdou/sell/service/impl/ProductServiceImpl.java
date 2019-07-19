package com.gdou.sell.service.impl;

import com.gdou.sell.Exception.SellException;
import com.gdou.sell.dao.ProductInfoDao;
import com.gdou.sell.dataobject.ProductInfo;
import com.gdou.sell.dto.CartDTO;
import com.gdou.sell.enums.ProductStatusEnum;
import com.gdou.sell.enums.ResultEnum;
import com.gdou.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductInfoDao productInfoDao ;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDao.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOS) {
        for(CartDTO cartDTO:cartDTOS ){
            Optional<ProductInfo> byId = productInfoDao.findById(cartDTO.getProductID());
            ProductInfo one = null;
            if(byId.isPresent()){
                one = byId.get();
            }
            if(one ==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = one.getProductStock() + cartDTO.getProductQuantity();
            one.setProductStock(result);
            productInfoDao.save(one);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOS) {
        for(CartDTO cartDTO:cartDTOS ){
            Optional<ProductInfo> byId = productInfoDao.findById(cartDTO.getProductID());
            if(!byId.isPresent()){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }else {
                ProductInfo one = byId.get();
                if(one ==null){
                    throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                }
                Integer result = one.getProductStock() - cartDTO.getProductQuantity();
                if(result<0){
                    throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
                }
                one.setProductStock(result);
                productInfoDao.save(one);
            }

        }
    }

    @Override
    @Transactional
    public ProductInfo onSale(String productId) {
        Optional<ProductInfo> one = productInfoDao.findById(productId);
        if(!one.isPresent()){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        ProductInfo productInfo = one.get();
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfoDao.save(productInfo);
        return productInfo;
    }

    @Override
    public ProductInfo offSale(String productId) {
        Optional<ProductInfo> one = productInfoDao.findById(productId);
        if(!one.isPresent()){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        ProductInfo productInfo = one.get();
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfoDao.save(productInfo);
        return productInfo;
    }
}
