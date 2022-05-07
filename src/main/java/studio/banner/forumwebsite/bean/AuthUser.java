package studio.banner.forumwebsite.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */

    private String email;

    /**
     * 手机号
     */

    private String phone;

    /**
     * 用户权限
     */

    private String role;

    /**
     * 用户注册时间
     */
    private String creatTime;

    /**
     * 成员姓名
     */
    private String memberName;

    /**
     * 成员性别
     */
    private String sex;

    /**
     * 成员方向
     */
    private String direction;

    /**
     * 成员年级
     */
    private String grade;

    /**
     * 成员生日
     */
    private String birthday;

    /**
     * 成员头像
     */
    private String headPortraitUrl;


    /**
     * 成员博客地址
     */
    private String blogUrl;

    /**
     * 成员Git地址(Gitee或者GitHub)
     */
    private String gitUrl;

    /**
     * 成员个性签名
     */
    private String personalizedSignature;

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

    /**
     * 成员自我介绍
     */
    private String personalIntroduction;

}
