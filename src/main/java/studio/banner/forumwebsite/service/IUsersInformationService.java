package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.UsersInformationBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/15 16:06
 */
public interface IUsersInformationService {
    /**
     * 增加用户信息到信息表
     * @param usersInformationBean
     * @return boolean
     */
    boolean insertUsersInformation(UsersInformationBean usersInformationBean);

    /**
     * 查询所有用户信息
     * @return List
     */
    List<UsersInformationBean> selectUsersInformation();

    /**
     * 删除用户信息
     * @param id
     * @return boolean
     */
    boolean deleteUsersInformation(Integer id);

    /**
     * 更改用户信息
     * @return
     */
    boolean updateUsersInformation(UsersInformationBean usersInformationBean);

    /**
     * 分页查询
     * @param page
     * @return IPage
     */
    IPage<UsersInformationBean> selectUsersInformationBeanPage(Integer page);

}
