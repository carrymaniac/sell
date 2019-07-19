package com.gdou.sell.dataobject;

import com.gdou.sell.enums.OrderStatusEnum;
import com.gdou.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@DynamicUpdate
public class OrderMaster {
    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    /** 订单金额 **/
    private BigDecimal orderAmount;
    /** 订单状态 默认状态为新下单为0  **/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 支付状态 默认为0未支付**/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    private Date createTime;

    private Date updateTime;
    /**
     *
     */

//    @Transient 可以避免JPA对其进行查找
//    private List<OrderDetail> orderDetailList;
}
