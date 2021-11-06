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
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_member")
@ApiModel(value="用户", description="注册时产生的基本账户")
public class UserBean {
    @TableId(type = IdType.AUTO)
    private Integer memberId;
    private String memberPhone;
    private String memberPassword;
    private String memberMail;
    private int memberAdmin;
    private String memberCode;
}