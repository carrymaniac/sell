package com.gdou.sell.service.impl;

import com.gdou.sell.dao.SellerInfoDao;
import com.gdou.sell.dataobject.SellerInfo;
import com.gdou.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Ha
 * @DATE 2019/6/5 20:38
 **/
@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerInfoDao sellerInfoDao;
    @Override
    public SellerInfo findByUsernameAndPassword(String username,String password) {
            return sellerInfoDao.findByUsernameAndPassword(username, password);
    }

    @Override
    public SellerInfo findByUsername(String username) {
        return sellerInfoDao.findByUsername(username);
    }
}
