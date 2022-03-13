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
 * @Date: 2022/3/7 20:25
 * @role:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_reply_like")
@ApiModel("回复点赞实体类")
public class ReplyLikeBean {

    /**
     * 点赞主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer likeId;
    /**
     * 点赞用户id
     */
    private Integer likeUserId;
    /**
     * 回复id
     */
    private Integer replyId;

    /**
     * 被点赞用户id
     */
    private Integer beLikeUserId;

    /**
     * 帖子id
     */
    private Integer postId;
    /**
     * 评论内容
     */
    private String replyContent;
    /**
     * 点赞时间
     */
    private String likeTime;
    /**
     * 是否展示
     */
    private Integer showInformation;
    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;
    /**
     *用户头像
     */
    @TableField(exist = false)
    private String headUrl;

}
