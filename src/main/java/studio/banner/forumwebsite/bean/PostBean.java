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
@ApiModel(value = "帖子对象")
public class PostBean {
    /**
     * 帖子id
     */
    @TableId(type = IdType.AUTO)
    private Integer postId;
    /**
     * 发帖用户id
     */
    @TableField
    @Valid
    @NotNull(message = "用户id不能为空")
    private Integer postMemberId;
    /**
     * 帖子标题
     */
    @NotNull(message = "帖子文章不能为空")
    private String postTitle;
    /**
     * 帖子内容
     */
    @NotNull(message = "帖子内容不能为空")
    private String postContent;
    /**
     * 帖子发布时间
     */
    private String postTime;
    /**
     * 帖子浏览量
     */
    private Integer postPageView;
    /**
     * 帖子评论量
     */
    private Integer postCommentNumber;
    /**
     * 帖子是否是转发帖子 0：原创  1：转发
     */
    private Integer postForward;
    /**
     * 帖子点赞量
     */
    private Integer postLikeNumber;
    /**
     * 图片地址
     */
    private String postImageAddress;
    /**
     * 帖子是否置顶
     */
    private Integer postTop;
}
