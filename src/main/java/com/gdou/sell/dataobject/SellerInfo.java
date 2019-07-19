package com.gdou.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author Ha
 * @DATE 2019/6/5 19:57
 **/
@Data
@Entity
public class SellerInfo {
    @Id
    private String id;
    private String username;
    private String openid;
    private String password;
}
