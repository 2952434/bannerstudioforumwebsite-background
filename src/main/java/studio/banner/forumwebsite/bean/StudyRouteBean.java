package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author: Ljx
 * @Date: 2022/3/5 21:01
 * @role:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_study_route")
@ApiModel(value = "学习路线")
public class StudyRouteBean {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotNull(message = "学习方向不能为空")
    private String studyDirection;

    @NotNull(message = "学习计划不能为空")
    private String studyContent;

    @NotNull(message = "学习阶段不能为空")
    private String studyTitle;

    @NotNull(message = "发表时间不能为空")
    private String publishTime;
}
