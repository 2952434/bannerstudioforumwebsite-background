package studio.banner.forumwebsite.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * @Author: Ljx
 * @Date: 2021/12/3 21:56
 * @role:
 */
@Data
@Document(indexName = "post")
public class PostBeanEs {
    @Id
    @Field(index = false,type = FieldType.Integer)
    private Integer id;
    @Field(analyzer = "ik_smart",searchAnalyzer = "ik_smart",store = true,type = FieldType.Text)
    private String title;
    @Field(analyzer = "ik_smart",searchAnalyzer = "ik_smart",store = true,type = FieldType.Text)
    private String context;
    @Field(index = false,store = true,type = FieldType.Integer)
    private Integer hits;
    @Field(index = false,store = true,type = FieldType.Text)
    private String time;
    @Field(index = false,store = true,type = FieldType.Integer)
    private Integer commentNumber;
    @Field(index = false,store = true,type = FieldType.Integer)
    private Integer likeNumber;
    @Field(index = false,store = true,type = FieldType.Integer)
    private Integer uid;
    @Field(index = false,store = true,type = FieldType.Integer)
    private Integer forward;
    @Field(index = false,store = true,type = FieldType.Integer)
    private String postImageAddress;
}
