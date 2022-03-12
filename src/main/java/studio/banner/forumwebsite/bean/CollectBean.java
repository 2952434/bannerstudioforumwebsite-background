package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 收藏对象
 *
 * @Author: Ljx
 * @Date: 2021/5/13 21:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tab_collect")
@ApiModel(value = "收藏对象")
public class CollectBean {
    /**
     * 收藏id
     */
    @TableId(value = "col_id", type = IdType.AUTO)
    private Integer colId;
    /**
     * 收藏的文章id
     */
    private Integer colArtId;
    /**
     * 收藏文件夹id
     */
    private Integer favoriteId;
    /**
     * 收藏用户id
     */
    private Integer cloUserId;
    /**
     * 收藏文章标题
     */
    @TableField(exist = false)
    private String colArtTit;
}
