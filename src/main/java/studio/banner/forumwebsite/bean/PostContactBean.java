package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 14:24
 * @role: 帖子类型关联表
 */
@Data
@ToString
@TableName("tab_post_contact")
@NoArgsConstructor
@AllArgsConstructor
public class PostContactBean {
    /**
     * 关联表id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 帖子类型id
     */
    @NotNull(message = "帖子类型id不为空")
    private Integer postTypeId;
    /**
     * 帖子id
     */
    @NotNull(message = "帖子id不为空")
    private Integer postId;

}
