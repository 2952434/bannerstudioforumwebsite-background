package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ljh
 * @Date: 2021/5/18 18:07
 * <p>
 * 用户粉丝关系表
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_user_attention")
@ApiModel(value = "用户关注关系", description = "保存用户之间的关注关系")
public class UserAttentionBean {
    /**
     * 关注id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 被关注人id
     */
    private Integer beAttentionId;
    /**
     * 关注人id
     */
    private Integer attentionId;
    /**
     * 关注时间
     */
    private String contactTime;
    /**
     * 是否展示
     */
    private Integer attentionShow;
    /**
     * 关注信息
     */
    private String attentionInformation;
    /**
     * 关注者昵称
     */
    @TableField(exist = false)
    private String attentionName;
    /**
     * 关注者头像
     */
    @TableField(exist = false)
    private String attentionHead;
    /**
     * 个性签名
     */
    @TableField(exist = false)
    private String memberSignature;
}
