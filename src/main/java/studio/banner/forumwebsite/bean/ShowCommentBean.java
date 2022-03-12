package studio.banner.forumwebsite.bean;

import lombok.Data;

import java.util.Objects;

/**
 * @Author: Ljx
 * @Date: 2022/3/12 10:52
 * @role:
 */
@Data
public class ShowCommentBean implements Comparable<ShowCommentBean> {
    /**
     * 评论,回复id
     */
    private Integer id;
    /**
     * 帖子id
     */
    private Integer postId;
    /**
     * 评论者id
     */
    private Integer commentUserId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 被评论的内容
     */
    private String title;
    /**
     * 评论时间
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

    public ShowCommentBean(CommentBean commentBean) {
        this.id = commentBean.getCommentId();
        this.postId = commentBean.getCommentPostId();
        this.commentUserId = commentBean.getPostMemberId();
        this.content = commentBean.getCommentContent();
        this.title = commentBean.getPostTitle();
        this.likeTime = commentBean.getCommentTime();
        this.userName = commentBean.getUserName();
        this.headUrl = commentBean.getHeadUrl();
    }

    public ShowCommentBean(ReplyBean replyBean) {
        this.id = replyBean.getReplyId();
        this.postId = replyBean.getPostId();
        this.commentUserId = replyBean.getReplyCommentMemberId();
        this.content = replyBean.getReplyContent();
        this.title = replyBean.getCommentContent();
        this.likeTime = replyBean.getReplyTime();
        this.userName = replyBean.getUserName();
        this.headUrl = replyBean.getHeadUrl();
    }

    @Override
    public int compareTo(ShowCommentBean o) {
        if (!Objects.equals(this.likeTime, o.likeTime)){
            return o.likeTime.compareTo(this.likeTime);
        }else {
            return o.userName.compareTo(this.userName);
        }
    }
}
