package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 21:12
 * @role:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_user_name")
@ToString
public class UserNameBean {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "用户姓名不能为空")
    private String userName;
}
