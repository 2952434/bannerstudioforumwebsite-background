package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: ljh
 * @Date: 2021/5/18 18:07
 * <p>
 * 用户粉丝关系表
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_contact")
@ApiModel(value = "用户关系", description = "保存用户之间的关注关系")
public class UserContactBean {
    /**
     * 关注id
     */
    @TableId(type = IdType.AUTO)
    private Integer attentionId;
    /**
     * 被关注人id
     */
    @TableField
    private Integer memberStar;
    /**
     * 关注人id
     */
    private Integer memberFan;
    /**
     * 关注时间
     */
    private String contactTime;
}
