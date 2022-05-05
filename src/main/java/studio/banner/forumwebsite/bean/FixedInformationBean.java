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
@TableName("tab_fixed_information")
@ApiModel(value = "用户固定信息接口", description = "从授权服务器获取的信息")
@ToString
public class FixedInformationBean {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户账号
     */
    @TableField(value = "users_account")
    private String usersAccount;
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
     * 用户年级
     */
    private String userGrade;

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

    public FixedInformationBean(AuthUser authUser,String usersAccount) {
        this.usersAccount = usersAccount;
        this.usersName = authUser.getUserName();
        this.usersDirection = authUser.getDirection();
        this.userGrade = authUser.getGrade();
        this.usersCompany = authUser.getMemberCompany();
        this.usersWork = authUser.getMemberWork();
        this.usersAddress = authUser.getMemberAddress();
        this.usersPay = authUser.getMemberPay();
        this.userId = authUser.getId();
    }
}
