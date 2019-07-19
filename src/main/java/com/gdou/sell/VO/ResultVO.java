package com.gdou.sell.VO;

import lombok.Data;

import java.util.List;

/**
 * http请求返回给前端的最外层的对象
 */
@Data
public class ResultVO<T> {
    /** 信息码 **/
    private Integer code ;
    /** 信息 **/
    private String msg ;
    private T data ;
}
