package com.gdou.sell.controller;

import com.gdou.sell.config.ProjectUrlConfig;
import com.gdou.sell.constant.CookieConstant;
import com.gdou.sell.constant.RedisConstant;
import com.gdou.sell.dataobject.SellerInfo;
import com.gdou.sell.enums.ResultEnum;
import com.gdou.sell.service.SellerService;
import com.gdou.sell.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 * @Author Ha
 * @DATE 2019/6/5 20:47
 **/
@Controller
@RequestMapping(value = "seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password,
                              Map<String,Object> maps, HttpServletResponse response){
            //用户名和密码去数据库匹配

        SellerInfo sellerInfo = sellerService.findByUsernameAndPassword(username,password);
        if(sellerInfo == null){
                maps.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
                maps.put("url","/sell/seller/order/list");
                return new ModelAndView("common/error/");
        }
            //设置Token去Redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),username,expire, TimeUnit.SECONDS);
        //设置Token到cookie中
        CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
        return new ModelAndView("redirect:"+"/seller/order/list");
    }

    public void loginOut(){

    }


}
