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
 * @Date: 2022/3/7 20:25
 * @role:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_reply_like")
@ApiModel("回复点赞实体类")
public class ReplyLikeBean {

    @TableId(type = IdType.AUTO)
    private Integer likeId;

    private Integer likeUserId;

    private Integer beLikeUserId;

    private Integer replyId;

    private String replyContent;

    private String replyLikeTime;

    private Integer replyPostId;

    private Integer replyShow;
}
