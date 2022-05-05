package studio.banner.forumwebsite.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Map;


/**
 * @Author: Ljx
 * @Date: 2021/12/3 21:56
 * @role: elasticsearch文章类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
     * 图片地址
     */
    @Field(index = false,store = true,type = FieldType.Text)
    private String imageAddress;
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
    /**
     * 用户头像
     */
    @Field(index = false,store = true,type = FieldType.Text)
    private String memberHead;

    /**
     * 置顶
     */
    @Field(index = false,store = true,type = FieldType.Integer)
    private Integer postTop;
    /**
     * 用户昵称
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true, type = FieldType.Text)
    private String memberName;

    public PostEsBean(Map<String,String> map) {
        this.postId = Integer.valueOf(String.valueOf(map.get("post_id")));
        this.postMemberId = Integer.valueOf(String.valueOf(map.get("post_member_id")));
        this.postTitle = map.get("post_title");
        this.postContent = map.get("post_content");
        this.postTime = map.get("post_time");
        this.postPageView = Integer.valueOf(String.valueOf(map.get("post_page_view")));
        this.commentNumber = Integer.valueOf(String.valueOf(map.get("post_comment_number")));
        this.likeNumber = Integer.valueOf(String.valueOf(map.get("post_like_number")));
        this.postType = map.get("post_type");
        this.postColNum = Integer.valueOf(String.valueOf(map.get("post_col_num")));
        this.memberHead = map.get("member_head");
        this.memberName = map.get("member_name");
        this.imageAddress = map.get("post_image_address");
        this.postTop = Integer.valueOf(String.valueOf(map.get("post_top")));
    }

}
