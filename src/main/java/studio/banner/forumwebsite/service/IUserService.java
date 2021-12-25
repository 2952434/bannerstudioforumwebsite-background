package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.UserBean;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/7 15:25
 * @role: 用户服务类接口
 */
public interface IUserService {
    /**
     * 插入用户
     *
     * @param userBean 用户实体
     * @return boolean
     */
    boolean insertUser(UserBean userBean);

    /**
     * 注册时查询是否已存在该账号
     *
     * @param memberPhone 手机号码
     * @return boolean
     */
    boolean selectAccount(String memberPhone);

    /**
     * 根据用户账号返回用户
     *
     * @param memberPhone 用户账号
     * @return UserBean
     */
    UserBean selectUser(String memberPhone);

    /**
     * 登陆时查询是否存在该账号与对应密码
     *
     * @param memberPhone    用户账号
     * @param memberPassword 用户密码
     * @return List<UserBean>
     * @throws Exception io异常
     */
    List<UserBean> selectUser(String memberPhone, String memberPassword) throws Exception;

    /**
     * 删除用户
     *
     * @param memberId 传入Id
     * @return boolean
     */
    boolean deleteUser(Integer memberId);

    /**
     * 更新用户
     *
     * @return boolean
     */
    boolean updateUser();

    /**
     * 修改密码
     *
     * @param memberId          输入Id
     * @param memberPassword    输入原密码
     * @param newMemberPassword 输入新密码
     * @param repeatPassword    重复新密码
     * @return boolean
     */
    boolean updateUserPassWord(Integer memberId, String memberPassword, String newMemberPassword, String repeatPassword);

    /**
     * 忘记密码，根据邮箱修改密码
     *
     * @param memberPhone       账号
     * @param memberMail        邮箱
     * @param code              验证码
     * @param newMemberPassword 新密码
     * @return boolean
     */
    boolean forgetPassWord(String memberPhone, String memberMail, String code, String newMemberPassword);

    /**
     * 分页查询用户
     *
     * @return UserBean
     */
    IPage<UserBean> selectUser();

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @param phone 账号
     * @return List<UserBean>
     */
    List<UserBean> sendMail(String email, String phone);


}
