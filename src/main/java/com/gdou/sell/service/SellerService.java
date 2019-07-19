package com.gdou.sell.service;

import com.gdou.sell.dataobject.SellerInfo;

/**
 * @Author Ha
 * @DATE 2019/6/5 20:36
 **/
public interface SellerService {
    SellerInfo findByUsernameAndPassword(String username,String password);

    SellerInfo findByUsername(String username);
}
