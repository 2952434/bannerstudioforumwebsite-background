package studio.banner.forumwebsite.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * @Author: Ljx
 * @Date: 2021/12/3 21:56
 * @role: elasticsearch文章类
 */
@Data
@Document(indexName = "post")
public class PostBeanEs {
    /**
     * 帖子id
     */
    @Id
    @Field(index = false, type = FieldType.Integer)
    private Integer id;
    /**
     * 帖子标题
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true, type = FieldType.Text)
    private String title;
    /**
     * 帖子内容
     */
    @Field(analyzer = "ik_smart", searchAnalyzer = "ik_smart", store = true, type = FieldType.Text)
    private String context;
    /**
     * 帖子浏览量
     */
    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer hits;
    /**
     * 帖子发布时间
     */
    @Field(index = false, store = true, type = FieldType.Text)
    private String time;
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
     * 作者id
     */
    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer uid;
    /**
     * 帖子是否是转发帖子 0：原创  1：转发
     */
    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer forward;
    /**
     * 帖子图片地址
     */
    @Field(index = false, store = true, type = FieldType.Integer)
    private String postImageAddress;
}
