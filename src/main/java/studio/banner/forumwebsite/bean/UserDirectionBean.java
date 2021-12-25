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
 * @role: 学习方向实体
 */
@Data
@ToString
@TableName("tab_user_direction")
@NoArgsConstructor
@AllArgsConstructor
public class UserDirectionBean {
    /**
     * 方向id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 学习方向如：Java
     */
    @NotNull(message = "学习方向不能为空")
    private String userDirection;
}
