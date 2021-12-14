package studio.banner.forumwebsite.bean;

import lombok.Data;

import java.util.Date;

/**
 * 为了方便后期获取token中的用户信息，将token中载荷部分单独封装成一个对象
 * @Author: Ljx
 * @Date: 2021/12/8 11:28
 * @role:
 */
@Data
public class Payload<T>{
    private String id;
    private T userInfo;
    private Date expiration;
}
