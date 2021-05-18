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
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_alluser_information")
@ApiModel(value="用户所有信息", description="可查看其他用户信息")
@ToString
public class UsersInformationBean {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "users_name")
    private String usersName;
    @TableField(value = "users_direction")
    private String usersDirection;
    @TableField(value = "users_phone")
    private String usersPhone;
    @TableField(value = "users_qq")
    private String usersQQ;
    @TableField(value = "users_wechat")
    private String usersWeChat;
    @TableField(value = "users_company")
    private String usersCompany;
    @TableField(value = "users_work")
    private String usersWork;
    @TableField(value = "users_address")
    private String usersAddress;
    @TableField(value = "users_pay")
    private Integer usersPay;
    @TableField(value = "user_id")
    private Integer userId;
}
