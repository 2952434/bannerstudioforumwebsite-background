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
 * @Author: Ljx
 * @Date: 2022/3/7 18:01
 * @role:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tab_collect_favorite")
@ApiModel(value = "用户收藏夹")
public class CollectFavoriteBean {
    /**
     * 收藏夹id
     */
    @TableId(value = "favorite_id", type = IdType.AUTO)
    private Integer favoriteId;
    /**
     * 收藏用户id
     */
    private Integer userId;
    /**
     * 收藏夹名
     */
    private String favoriteName;
    /**
     * 0 公开 1 隐私
     */
    private Integer privacy;

}
