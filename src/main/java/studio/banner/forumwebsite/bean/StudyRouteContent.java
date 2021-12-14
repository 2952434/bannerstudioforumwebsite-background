package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author hyy
 * @date 2021/5/14 16:22
 * @role StudyRouteContent实体
 */
@Data
@ToString
@TableName("study_route_content")
@NoArgsConstructor
@AllArgsConstructor
public class StudyRouteContent {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "学习内容不能为空")
    private String studyContent;
    @NotNull(message = "内容序号不能为空")
    private Integer serialNumber;
    @ApiModelProperty(hidden = true)
    private Integer directionId;
}
