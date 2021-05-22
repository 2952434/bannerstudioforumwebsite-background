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
 * @Description: 帖子实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_post")
@ApiModel(value="帖子对象")
public class PostBean {
    @TableId(type = IdType.AUTO)
    private Integer postId;
    @TableField
    @NotNull(message = "用户id不能为空")
    private Integer postMemberId;
    @NotNull(message = "帖子内容不能为空")
    private String postContent;
    @NotNull(message = "帖子创建时间不能为空")
    private String postTime;
    private Integer postPageview;
    private Integer postCommentNumber;
    private Integer postForward;
    private Integer postLikeNumber;
    private String postImageAddress;
}
