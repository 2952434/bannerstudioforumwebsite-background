package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.UserContactBean;

import java.util.List;

/**
 * @Author: Mo
 * @Date: 2021/5/18 18:34
 * @role: 关注关系服务接口
 */
public interface IUserContactService {
    /**
     * 新增关注
     *
     * @param userContactBean 关注实体
     * @return boolean
     */
    boolean insertContact(UserContactBean userContactBean);

    /**
     * 取消关注
     *
     * @param attentionId 关注id
     * @return boolean
     */
    boolean deleteContact(Integer attentionId);

    /**
     * 查询是否存在关注关系，返回对象
     *
     * @param memberFan  粉丝id
     * @param memberStar 被关注者id
     * @return List<UserContactBean>
     */
    List<UserContactBean> contacted(Integer memberFan, Integer memberStar);

    /**
     * 根据用户Id查询其粉丝
     *
     * @param memberStar 用户id
     * @return List<UserContactBean>
     */
    List<UserContactBean> fans(Integer memberStar);

    /**
     * 根据Id查询其关注的人
     *
     * @param memberFan 粉丝id
     * @return List<UserContactBean>
     */
    List<UserContactBean> stars(Integer memberFan);
}
