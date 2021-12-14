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
 * @author hyy
 * @date 2021/5/14 16:24
 * @role StudyRoute实体
 */
@Data
@ToString
@TableName("study_route")
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoute {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "学习方向不能为空")
    private String studyDirection;
    @NotNull(message = "学习阶段不能为空")
    private String studyStage;
    @NotNull(message = "阶段编号不能为空")
    private Integer stageNumber;
    @NotNull(message = "考核形式不能为空")
    private String examinationForm;

}
