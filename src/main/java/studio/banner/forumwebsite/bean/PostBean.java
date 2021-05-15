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
/**
 * @author HYK
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_post")
@ApiModel(value="帖子对象")
public class PostBean {
    @TableId(type = IdType.AUTO)
    protected Integer postId;
    @TableField
    @NotNull(message = "用户id不能为空")
    protected Integer postMemberId;
    @NotNull(message = "帖子内容不能为空")
    protected String postContent;
    @NotNull(message = "帖子创建时间不能为空")
    protected String postTime;
    protected Integer postPageview;
    protected Integer postCommentNumber;
    protected Integer postForward;
    protected Integer postLikeNumber;
}
