package com.gdou.sell.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by
 *  用于表单验证用
 * @author Lusty
 * @date 2019-05-24 11:38
 */
@Data
public class OrderForm {
    @NotNull(message = "姓名必填")
    private String name;

    @NotNull(message = "手机号必填")
    private String phone;

    @NotNull(message = "地址必填")
    private String address;

    @NotNull(message = "openID必填")
    private String openid;

    @NotNull(message = "购物车不能为空")
    private String items;

}
