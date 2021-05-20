package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author: ljh
 * @Date: 2021/5/17 11:33
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_member_information")
@ApiModel(value="用户信息", description="注册时伴随产生的用户信息表")
@ToString
public class UserMsgBean {
    private Integer memberId;
    private String memberName;
    private String memberSex;
    private Integer memberAge;
    private String memberTime;
    private String memberHead;
    private Integer memberFans;
    private Integer memberAttention;
    }
