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
 * @Author: Ljx
 * @Date: 2021/5/13 21:56
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tab_collect")
@ApiModel(value="收藏对象")
public class CollectBean {
    @TableId(value = "col_id",type = IdType.AUTO)
    private Integer colId;
    @TableField(value = "col_art_id")
    private Integer colArtId;
    @TableField(value = "col_art_tit")
    private String colArtTit;
    @TableField(value = "clo_user_id")
    private Integer cloUserId;

}
