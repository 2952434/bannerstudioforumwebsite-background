package studio.banner.forumwebsite.service;

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
     * 登陆时查询是否存在该账号与对应密码
     * @param memberAccountNumber
     * @return boolean
     */
    boolean selectUser(Integer memberAccountNumber,String memberPassword);

    /**
     * 删除用户
     * @return boolean
     */
    boolean deleteUser();

    /**
     * 更新用户
     * @return boolean
     */
    boolean updateUser();

    /**
     * 分页查询用户
     * @return UserBean
     */
    IPage<UserBean> selectUser();
}
