package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/15/15:07
 * @Description: 评论实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_comment")
@ApiModel(value = "评论回复对象")
public class CommentBean {
    /**
     * 评论id
     */
    @TableId(type = IdType.AUTO)
    private Integer commentId;
    /**
     * 被评论的帖子id
     */
    @TableField
    @NotNull(message = "帖子id不能为空")
    private Integer commentPostId;
    /**
     * 评论的用户id
     */
    @NotNull(message = "评论用户id不能为空")
    private Integer commentMemberId;
    /**
     * 评论点赞量
     */
    private Integer commentLikeNumber;
    /**
     * 评论内容
     */
    @NotNull(message = "评论内容不能为空")
    private String commentContent;
    /**
     * 评论的时间
     */
    @NotNull(message = "评论时间不能为空")
    private String commentTime;


}
