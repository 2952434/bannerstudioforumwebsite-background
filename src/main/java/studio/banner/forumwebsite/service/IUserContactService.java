package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.UserContactBean;

import java.util.List;

/**
 * @Author: Mo
 * @Date: 2021/5/18 18:34
 */
public interface IUserContactService {
    /**
     * 新增关注
     * @param userContactBean
     * @return boolean
     */
    boolean insertContact(UserContactBean userContactBean);

    /**
     * 取消关注
     * @param attentionId
     * @return boolean
     */
    boolean deleteContact(Integer attentionId);

    /**
     * 查询是否存在关注关系，返回对象
     * @param memberFan
     * @param memberStar
     * @return list
     */
    List<UserContactBean> contacted(Integer memberFan, Integer memberStar);
}
