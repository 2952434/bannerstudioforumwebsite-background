package studio.banner.forumwebsite.bean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.rmi.MarshalException;
import java.security.PrivateKey;
import java.util.Date;

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
@ApiModel(value="评论回复对象")
public class CommentBean {
    @TableId(type = IdType.AUTO)
    private Integer commentId;
    @TableField
    @NotNull(message = "帖子id不能为空")
    private Integer commentPostId;
    @NotNull(message = "评论用户id不能为空")
    private Integer commentMemberId;
    private Integer commentLikeNumber;
    @NotNull(message = "评论内容不能为空")
    private String commentContent;
    @NotNull(message = "评论时间不能为空")
    private String commentTime;


}
