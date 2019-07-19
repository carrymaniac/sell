package com.gdou.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gdou.sell.enums.ProductStatusEnum;
import com.gdou.sell.util.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
public class ProductInfo {
    /** 名字 **/
    private String productName;
    /** 类目编号 **/
    private Integer categoryType;
    /** 价格 **/
    private BigDecimal productPrice;
    /** 商品ID **/
    @Id
    private String productId;
    /** 库存 **/
    private Integer productStock;
    /** 小图 **/
    private String productIcon;
    /** 商品状态 **/
    private Integer productStatus;
    /** 商品描述 **/
    private String productDescription;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }


}
