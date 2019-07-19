package com.gdou.sell.service;

import com.gdou.sell.dataobject.ProductCategory;
import com.gdou.sell.dataobject.ProductInfo;
import com.gdou.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    /**
     * 查询所有上架的商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询所有商品(分页版本)
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     *
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);


    void increaseStock(List<CartDTO> cartDTOS);

    void decreaseStock(List<CartDTO> cartDTOS);

    ProductInfo onSale(String productId);

    /**
     * 下架
     * @param productId
     * @return
     */
    ProductInfo offSale(String productId);
}
