package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 21:12
 * @role: 用户名字实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tab_user_name")
@ToString
public class UserNameBean {
    /**
     * 名字id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户姓名
     */
    @NotNull(message = "用户姓名不能为空")
    private String userName;
}
