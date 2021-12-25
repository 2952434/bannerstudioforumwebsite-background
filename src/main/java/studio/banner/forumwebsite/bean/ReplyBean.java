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
 * @Date: 2021/05/17/11:56
 * @Description: 回复实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_reply")
@ApiModel("回复实体类")
public class ReplyBean {
    /**
     * 回复id
     */
    @TableId(type = IdType.AUTO)
    private Integer replyId;
    /**
     * 评论id
     */
    @TableField
    private Integer commentId;
    /**
     * 回复作者id
     */
    private Integer replyMemberId;
    /**
     * 被回复作者id
     */
    private Integer replyCommentMemberId;
    /**
     * 回复内容
     */
    @NotNull(message = "回复内容不能为空")
    private String replyContent;
    /**
     * 回复点赞量
     */
    private Integer replyLikeNumber;
    /**
     * 回复时间
     */
    private String replyTime;


}
