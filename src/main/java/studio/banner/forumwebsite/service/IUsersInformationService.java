package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import studio.banner.forumwebsite.bean.UsersInformationBean;


/**
 * @Author: Ljx
 * @Date: 2021/5/15 16:06
 */
public interface IUsersInformationService {
    /**
     * 增加用户信息到信息表
     *
     * @param usersInformationBean 用户信息实体
     * @return boolean
     */
    boolean insertUsersInformation(UsersInformationBean usersInformationBean);


    /**
     * 查询用户信息通过id
     *
     * @param id 用户id
     * @return boolean
     */
    boolean selectUsersInformationById(Integer id);

    /**
     * 删除用户信息
     *
     * @param id 用户id
     * @return boolean
     */
    boolean deleteUsersInformation(Integer id);

    /**
     * 更改用户信息
     *
     * @param usersInformationBean 用户信息实体
     * @return boolean
     */
    boolean updateUsersInformation(UsersInformationBean usersInformationBean);

    /**
     * 分页查询
     *
     * @param page 页数
     * @return IPage<UsersInformationBean>
     */
    IPage<UsersInformationBean> selectUsersInformationBeanPage(Integer page);

    /**
     * 模糊查询用户
     *
     * @param page 第几页
     * @param dim  模糊查询字段
     * @return IPage<UsersInformationBean>
     */
    IPage<UsersInformationBean> selectUserInformationDimPage(Integer page, String dim);
}
