package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.FixedInformationBean;


/**
 * @Author: Ljx
 * @Date: 2021/5/15 16:06
 */
public interface IFixedInformationService {

    /**
     * 增加用户信息到信息表
     *
     * @param fixedInformationBean 用户信息实体
     * @return boolean
     */
    boolean insertUsersInformation(FixedInformationBean fixedInformationBean);


    /**
     * 查询用户信息通过id
     *
     * @param id 用户id
     * @return boolean
     */
    FixedInformationBean selectUsersInformationById(Integer id);

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
     * @param fixedInformationBean 用户信息实体
     * @return boolean
     */
    boolean updateUsersInformation(FixedInformationBean fixedInformationBean);


}
