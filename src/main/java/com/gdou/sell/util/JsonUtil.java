package com.gdou.sell.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author Ha
 * @DATE 2019/6/7 11:30
 **/
public class JsonUtil {
    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
