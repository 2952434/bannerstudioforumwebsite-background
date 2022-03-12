package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
     * 评论id
     */
    private Integer commentId;
    /**
     * 被点赞者id
     */
    private Integer beLikeUserId;
    /**
     * 帖子id
     */
    private Integer postId;
    /**
     * 回复内容
     */
    private String commentContent;
    /**
     * 点赞时间
     */
    private String likeTime;
    /**
     * 是否展示
     */
    private Integer show;

    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;
    /**
     *
     */
    @TableField(exist = false)
    private String headUrl;
}
