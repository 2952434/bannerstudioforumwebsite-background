package studio.banner.forumwebsite.bean;

import lombok.Data;

import java.util.Objects;

/**
 * @Author: Ljx
 * @Date: 2022/3/11 22:18
 * @role:
 */
@Data
public class ShowLikeBean implements Comparable<ShowLikeBean>{
    /**
     * 点赞id
     */
    private Integer likeId;
    /**
     * 帖子id
     */
    private Integer postId;
    /**
     * 点赞者id
     */
    private Integer likeUserId;
    /**
     * 点赞内容
     */
    private String content;
    /**
     * 点赞时间
     */
    private String likeTime;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String headUrl;

    public ShowLikeBean(CommentLikeBean commentLikeBean) {
        this.likeId = commentLikeBean.getLikeId();
        this.postId = commentLikeBean.getPostId();
        this.likeUserId = commentLikeBean.getLikeUserId();
        this.content = commentLikeBean.getCommentContent();
        this.likeTime = commentLikeBean.getLikeTime();
        this.userName = commentLikeBean.getUserName();
        this.headUrl = commentLikeBean.getHeadUrl();
    }
    public ShowLikeBean(PostLikeBean postLikeBean) {
        this.likeId = postLikeBean.getLikeId();
        this.postId = postLikeBean.getLikePostId();
        this.likeUserId = postLikeBean.getUserLikeId();
        this.content = postLikeBean.getLikePostTitle();
        this.likeTime = postLikeBean.getLikeTime();
        this.userName = postLikeBean.getUserName();
        this.headUrl = postLikeBean.getHeadUrl();
    }
    public ShowLikeBean(ReplyLikeBean replyLikeBean) {
        this.likeId = replyLikeBean.getLikeId();
        this.postId = replyLikeBean.getPostId();
        this.likeUserId = replyLikeBean.getLikeUserId();
        this.content = replyLikeBean.getReplyContent();
        this.likeTime = replyLikeBean.getLikeTime();
        this.userName = replyLikeBean.getUserName();
        this.headUrl = replyLikeBean.getHeadUrl();
    }

    @Override
    public int compareTo(ShowLikeBean o) {
        if (!Objects.equals(this.likeTime, o.likeTime)){
            return o.likeTime.compareTo(this.likeTime);
        }else {
            return o.userName.compareTo(this.likeTime);
        }
    }
}
