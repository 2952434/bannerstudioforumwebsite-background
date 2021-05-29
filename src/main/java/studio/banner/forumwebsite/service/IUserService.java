package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.UserBean;

import javax.servlet.http.HttpSession;

/**
 * @Author: Ljx
 * @Date: 2021/5/7 15:25
 */
public interface IUserService {
    /**
     * 插入用户
     * @param userBean
     * @return boolean
     */
    boolean insertUser(UserBean userBean);

    /**
     * 注册时查询是否已存在该账号
     * @param memberPhone
     * @return boolean
     */
    boolean selectAccount(String memberPhone);

    /**
     * 根据用户账号返回用户
     * @param memberPhone
     * @return boolean
     */
    UserBean selectUser(String memberPhone);

    /**
     * 登陆时查询是否存在该账号与对应密码
     * @param memberPhone
     * @param memberPassword
     * @return boolean
     */
    boolean selectUser(String memberPhone,String memberPassword);

    /**
     * 删除用户
     * @param memberId  传入Id
     * @return boolean
     */
    boolean deleteUser(Integer memberId);

    /**
     * 更新用户
     * @return boolean
     */
    boolean updateUser();

    /**
     * 更改用户密码
     * @param memberId
     * @param memberPassword
     * @param newMemberPassword
     * @param repeatPassword
     * @return boolean
     */
    boolean updateUserPassWord(Integer memberId, String memberPassword, String newMemberPassword,String repeatPassword);

    /**
     * 忘记密码，根据邮箱修改密码
     * @param memberPhone
     * @param memberMail
     * @param code
     * @param newMemberPassword
     * @return boolean
     */
    boolean forgetPassWord(String memberPhone,String memberMail,String code,String newMemberPassword);

    /**
     * 分页查询用户
     * @return UserBean
     */
    IPage<UserBean> selectUser();
    boolean sendMail(String email, HttpSession session);
}
