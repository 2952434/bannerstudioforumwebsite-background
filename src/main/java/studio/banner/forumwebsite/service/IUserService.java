package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.UserBean;

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
     * @param memberAccountNumber
     * @return boolean
     */
    boolean selectAccount(Integer memberAccountNumber);

    /**
     * 根据用户账号返回用户
     * @param memberAccount
     * @return boolean
     */
    UserBean selectUser(Integer memberAccount);

    /**
     * 登陆时查询是否存在该账号与对应密码
     * @param memberAccountNumber
     * @param memberPassword
     * @return boolean
     */
    boolean selectUser(Integer memberAccountNumber,String memberPassword);

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
     * @return boolean
     */

    boolean updateUserPassWord(Integer memberId, String memberPassword , String newMemberPassword);

    /**
     * 分页查询用户
     * @return UserBean
     */
    IPage<UserBean> selectUser();
}
