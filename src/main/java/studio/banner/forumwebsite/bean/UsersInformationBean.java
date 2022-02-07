package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: Ljx
 * @Date: 2021/5/15 15:45
 * 所有用户信息实体
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_alluser_information")
@ApiModel(value = "用户所有信息", description = "可查看其他用户信息")
@ToString
public class UsersInformationBean {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户姓名
     */
    @TableField(value = "users_name")
    private String usersName;
    /**
     * 用户方向
     */
    @TableField(value = "users_direction")
    private String usersDirection;
    /**
     * 用户手机号
     */
    @TableField(value = "users_phone")
    private String usersPhone;

    /**
     * 用户QQ号
     */
    @TableField(value = "users_qq")
    private String usersQQ;
    /**
     * 用户微信号
     */
    @TableField(value = "users_wechat")
    private String usersWeChat;
    /**
     * 用户公司
     */
    @TableField(value = "users_company")
    private String usersCompany;
    /**
     * 用户的工作
     */
    @TableField(value = "users_work")
    private String usersWork;
    /**
     * 用户工作地址
     */
    @TableField(value = "users_address")
    private String usersAddress;
    /**
     * 用户薪资
     */
    @TableField(value = "users_pay")
    private Integer usersPay;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
}
