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
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_contact")
@ApiModel(value="用户关系", description="保存用户之间的关注关系")
public class UserContactBean {
    @TableId(type = IdType.AUTO)
    private Integer attentionId;
    @TableField
    private Integer memberStar;
    private Integer memberFan;
    private String contactTime;
}
