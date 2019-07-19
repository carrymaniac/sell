package com.gdou.sell.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class OrderDetail {
    private String orderId;
    @Id
    private String detailId;
    private String productId;
    /** 商品单价 **/
    private BigDecimal productPrice;
    /** 商品数量 **/
    private Integer productQuantity;
    /** 商品名称 **/
    private String productName;
    private String productIcon;
}
