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
 * @Date: 2021/12/12 19:37
 * @role:
 */

@Data
@ToString
@TableName("tab_user_grade_name_dir_contact")
@NoArgsConstructor
@AllArgsConstructor
public class UserGradeContactBean {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "用户所在年级id不能为空")
    private Integer userGradeId;
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotNull(message = "用户姓名id不能为空")
    private Integer userNameId;
    @NotNull(message = "用户方向id不能为空")
    private Integer userDirectionId;
}
