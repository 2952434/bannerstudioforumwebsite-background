package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Ljx
 * @Date: 2022/3/7 20:21
 * @role:
 */
@Data
@ToString
@TableName("tab_post_like")
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeBean {

    @TableId(type = IdType.AUTO)
    private Integer likeId;

    private Integer userLikeId;

    private Integer beUserLikeId;

    private Integer likePostId;

    private String likePostTitle;

    private String likeTime;

    private Integer likeShow;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String headUrl;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
