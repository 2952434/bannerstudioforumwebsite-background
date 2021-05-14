package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.UserBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/14 21:00
 */
public interface IListenerService {
    /**
     * 查询所有用户
     * @return
     */
    List<UserBean> selectAllUser();
}
