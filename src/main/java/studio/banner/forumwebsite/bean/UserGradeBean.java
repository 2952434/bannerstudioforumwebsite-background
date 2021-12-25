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
 * @role: 年级实体
 */
@Data
@ToString
@TableName("tab_user_grade")
@NoArgsConstructor
@AllArgsConstructor
public class UserGradeBean {
    /**
     * 年级id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户年级如：2020级
     */
    @NotNull(message = "班级不能为空")
    private String userGrade;
}
