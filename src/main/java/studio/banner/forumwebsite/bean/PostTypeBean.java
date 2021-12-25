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
 * @Date: 2021/12/12 14:24
 * @role: 帖子类型表
 */
@Data
@ToString
@TableName("tab_post_type")
@NoArgsConstructor
@AllArgsConstructor
public class PostTypeBean {
    /**
     * 帖子类型id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 帖子类型
     */
    @NotNull(message = "帖子类型不为空")
    private String postType;
}
