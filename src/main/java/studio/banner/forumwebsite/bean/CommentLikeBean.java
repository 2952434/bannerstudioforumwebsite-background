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
 * @Date: 2022/3/7 18:07
 * @role:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_comment_like")
@ApiModel(value = "评论点赞实体")
public class CommentLikeBean {
    /**
     * 评论点赞主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer likeId;
    /**
     * 点赞人id
     */
    private Integer likeUserId;
    /**
     * 被点赞人id
     */
    private Integer beLikeUserId;
    /**
     * 点赞时间
     */
    private String commentLikeTime;
    /**
     * 点赞的内容
     */
    private String commentContent;
    /**
     * 评论帖子id
     */
    private Integer commentPostId;
    /**
     * 是否展示信息 不展示：0 展示：1
     */
    private Integer commentShow;
}
