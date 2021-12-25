package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: Ljh
 * @Date: 2021/5/11 16:20
 * <p>
 * 用户账号实体
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_member")
@ApiModel(value = "用户", description = "注册时产生的基本账户")
public class UserBean {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer memberId;
    /**
     * 用户手机号
     */
    private String memberPhone;
    /**
     * 用户密码
     */
    private String memberPassword;
    /**
     * 用户邮箱
     */
    private String memberMail;
    /**
     * 用户权限
     */
    private String memberAdmin;
    /**
     * 用户验证码
     */
    private String memberCode;
}