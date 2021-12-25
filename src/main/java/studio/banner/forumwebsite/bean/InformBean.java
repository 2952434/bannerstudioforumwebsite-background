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
 * @role: 通知实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_inform")
@ApiModel(value = "通知实体")
public class InformBean {
    /**
     * 通知id
     */
    @TableId(type = IdType.AUTO)
    private Integer informId;
    /**
     * 通知标题
     */
    private String informTitle;
    /**
     * 通知内容
     */
    private String informContent;
    /**
     * 通知发布时间
     */
    private String informTime;
    /**
     * 通知发布的作者
     */
    private String informAuthor;
}
