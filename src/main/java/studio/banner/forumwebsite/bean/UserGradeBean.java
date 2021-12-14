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
 * @Date: 2021/12/12 19:33
 * @role:
 */
@Data
@ToString
@TableName("tab_user_grade")
@NoArgsConstructor
@AllArgsConstructor
public class UserGradeBean {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "班级不能为空")
    private String userGrade;
}
