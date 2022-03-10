package studio.banner.forumwebsite.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author: Ben
 * @Date: 2022/1/6 1:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthUser {
    
    /**
     * id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 成员方向
     */
    private String direction;

    /**
     * 成员年级
     */
    private String grade;

    /**
     * 成员QQ号
     */
    private String memberQQ;
    /**
     * 成员微信号
     */
    private String memberWeChat;
    /**
     * 成员公司
     */
    private String memberCompany;
    /**
     * 成员的工作
     */
    private String memberWork;
    /**
     * 用户工作地址
     */
    private String memberAddress;
    /**
     * 成员薪资
     */
    private Integer memberPay;

}
