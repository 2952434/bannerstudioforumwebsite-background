package studio.banner.forumwebsite.bean;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ljx
 * @Date: 2021/12/8 11:28
 * @role: 为了方便后期获取token中的用户信息，将token中载荷部分单独封装成一个对象
 */
@Data
public class Payload<T> {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户信息
     */
    private T userInfo;
    /**
     * 创建时间
     */
    private Date expiration;
}
