package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.UserBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/14 21:00
 * @role: 监听用户服务层接口
 */
public interface IListenerService {
    /**
     * 查询用户
     *
     * @param userNum 用户账号
     * @return List<UserBean>
     */
    List<UserBean> selectAllUser(Integer userNum);
}
