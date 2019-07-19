package com.gdou.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISH(1,"订单已完成"),
    CANCEL(2,"已取消"),
    DDDD(2,"msg")
    ;

    private Integer code;
    private String msg;
    OrderStatusEnum(Integer code,String msg) {
        this.code =code;
        this.msg = msg;
    }

}
