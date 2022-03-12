package studio.banner.forumwebsite.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotNull;


/**
 * @Author: Ljx
 * @Date: 2021/12/3 21:56
 * @role: elasticsearch文章类
 */
@Data
@Document(indexName = "post")
public class PostEsBean {
    /**
     * 帖子id
     */
    @Id
    @Field(index = false, type = FieldType.Integer)
    private Integer postId;

    /**
     * 作者id
     */
    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer postMemberId;
    /**
     * 帖子标题
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true, type = FieldType.Text)
    private String postTitle;
    /**
     * 帖子内容
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true, type = FieldType.Text)
    private String postContent;

    /**
     * 帖子发布时间
     */
    @Field(index = false, store = true, type = FieldType.Text)
    private String postTime;

    /**
     * 帖子浏览量
     */
    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer postPageView;

    /**
     * 帖子评论量
     */
    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer commentNumber;
    /**
     * 帖子点赞量
     */
    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer likeNumber;
    /**
     * 帖子标签
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true, type = FieldType.Text)
    private String postType;
    /**
     * 帖子收藏数量
     */
    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer postColNum;

    public PostEsBean(PostBean postBean) {
        this.postId = postBean.getPostId();
        this.postMemberId = postBean.getPostMemberId();
        this.postTitle = postBean.getPostTitle();
        this.postContent = postBean.getPostContent();
        this.postTime = postBean.getPostTime();
        this.postPageView = postBean.getPostPageView();
        this.commentNumber = postBean.getPostCommentNumber();
        this.likeNumber = postBean.getPostLikeNumber();
        this.postType = postBean.getPostType();
        this.postColNum = postBean.getPostColNum();
    }
}
