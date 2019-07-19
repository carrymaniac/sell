package com.gdou.sell.dao;

import com.gdou.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Ha
 * @DATE 2019/6/5 20:02
 **/
public interface SellerInfoDao extends JpaRepository<SellerInfo,String> {
    SellerInfo findByUsernameAndPassword(String username,String password);
    SellerInfo findByUsername(String username);
}
