package studio.banner.forumwebsite.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: ljh
 * @Date: 2021/5/17 11:33
 * <p>
 * 用户信息实体
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_member_information")
@ApiModel(value = "用户信息", description = "注册时伴随产生的用户信息表")
@ToString
public class UserMsgBean {
    /**
     * 用户id
     */
    private Integer memberId;
    /**
     * 用户昵称
     */
    private String memberName;
    /**
     * 用户性别
     */
    private String memberSex;
    /**
     * 用户年龄
     */
    private Integer memberAge;
    /**
     * 用户账号创建时间
     */
    private String memberTime;
    /**
     * 用户头像地址
     */
    private String memberHead;
    /**
     * 用户粉丝数
     */
    private Integer memberFans;
    /**
     * 用户关注数
     */
    private Integer memberAttention;
    /**
     * 用户生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date memberBirthday;
    /**
     * 用户个性签名
     */
    private String memberSignature;
}
