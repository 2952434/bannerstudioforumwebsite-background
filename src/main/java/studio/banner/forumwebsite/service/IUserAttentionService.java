package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserAttentionBean;

import java.util.List;

/**
 * @Author: Mo
 * @Date: 2021/5/18 18:34
 * @role: 关注关系服务接口
 */
public interface IUserAttentionService {

    /**
     * 新增关注
     *
     * @param userAttentionBean 关注实体
     * @return boolean
     */
    RespBean insertContact(UserAttentionBean userAttentionBean);

    /**
     * 取消关注
     *
     * @param attentionId 关注id
     * @return boolean
     */
    boolean deleteContact(Integer attentionId,Integer beAttentionId);

    /**
     * 查询是否存在关注关系，返回对象
     *
     * @param memberFan  粉丝id
     * @param memberStar 被关注者id
     * @return List<UserAttentionBean>
     */
    boolean contacted(Integer memberFan, Integer memberStar);

    /**
     * 根据用户Id查询其粉丝数
     * @param memberId
     * @return
     */
    Integer selectFansByMemberId(Integer memberId);

    /**
     * 根据用户Id查询其粉丝
     * @param memberStar
     * @param page
     * @return
     */
    List<UserAttentionBean> fans(Integer memberStar,Integer page);


    /**
     * 通过用户id查询其粉丝数
     * @param memberId
     * @return
     */
    Integer selectStarsByMemberId(Integer memberId);

    /**
     * 根据Id查询其关注的人
     * @param memberFan
     * @param page
     * @return
     */
    List<UserAttentionBean> stars(Integer memberFan,Integer page);
}
