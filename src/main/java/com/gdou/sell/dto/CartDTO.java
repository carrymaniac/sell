package com.gdou.sell.dto;

import lombok.Data;

@Data
public class CartDTO {
    /**
     * 商品ID
     */
    private String productID;
    /*
        商品数量
     */
    private Integer productQuantity;

    public CartDTO(String productID, Integer productQuantity) {
        this.productID = productID;
        this.productQuantity = productQuantity;
    }
}
