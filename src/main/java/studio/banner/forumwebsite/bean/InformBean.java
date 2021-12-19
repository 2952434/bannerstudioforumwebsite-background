package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Ljx
 * @Date: 2021/12/19 14:01
 * @role:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_inform")
@ApiModel(value="通知实体")
public class InformBean {

    @TableId(type = IdType.AUTO)
    private Integer informId;
    private String informTitle;
    private String informContent;
    private String informTime;
    private String informAuthor;
}
