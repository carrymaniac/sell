package com.gdou.sell.util;

import java.util.Random;

public class KeyUtil {
    //生成唯一组件
    //加入synchronized防止多线程生产相同ID
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer integer =random.nextInt(900000)+100000;

        return System.currentTimeMillis()+ String.valueOf(integer);
    }
}
