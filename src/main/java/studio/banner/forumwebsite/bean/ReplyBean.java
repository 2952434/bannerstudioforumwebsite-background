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
    @TableId(type = IdType.AUTO)
    private Integer replyId;
    @TableField
    private Integer commentId;
    private Integer replyMemberId;
    private Integer replyCommentMemberId;
    @NotNull(message = "回复内容不能为空")
    private String replyContent;
    private Integer replyLikeNumber;
    private String replyTime;


}
