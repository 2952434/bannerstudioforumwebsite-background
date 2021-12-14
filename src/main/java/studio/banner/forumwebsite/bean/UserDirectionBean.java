package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author: Ljx
 * @Date: 2021/12/13 15:44
 * @role:
 */
@Data
@ToString
@TableName("tab_user_direction")
@NoArgsConstructor
@AllArgsConstructor
public class UserDirectionBean {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "用户方向不能为空")
    private String userDirection;
}
