package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.validation.constraints.NotNull;
/**
 * @Author: Ljx
 * @Date: 2021/12/12 14:24
 * @role:
 */
@Data
@ToString
@TableName("tab_post_contact")
@NoArgsConstructor
@AllArgsConstructor
public class PostContactBean {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "帖子类型id不为空")
    private Integer postTypeId;
    @NotNull(message = "帖子id不为空")
    private Integer postId;

}
