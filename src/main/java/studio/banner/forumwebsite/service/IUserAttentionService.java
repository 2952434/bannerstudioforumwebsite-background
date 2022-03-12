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
     * @param attentionId
     * @param beAttentionId
     * @return
     */
    boolean deleteContact(Integer attentionId,Integer beAttentionId);

    /**
     * 查询是否存在关注关系
     *
     * @param memberFan  粉丝id
     * @param memberStar 被关注者id
     * @return
     */
    boolean contacted(Integer memberFan, Integer memberStar);

    /**
     * 根据用户Id查询其粉丝数
     * @param memberId
     * @return
     */
    Integer selectFansByMemberId(Integer memberId);


    /**
     * 根据Id分页查询其关注的人
     * @param memberFan
     * @param page
     * @return
     */
    List<UserAttentionBean> stars(Integer memberFan,Integer page);

    /**
     * 根据用户Id查询其粉丝
     * @param memberStar
     * @param page
     * @return
     */
    List<UserAttentionBean> fans(Integer memberStar,Integer page);

    /**
     * 根据用户Id查询其粉丝信息
     * @param memberId
     * @param page
     * @return
     */
    List<UserAttentionBean> selectAttentionInformation(Integer memberId,Integer page);

    /**
     * 通过用户id查询其关注数数
     * @param memberId
     * @return
     */
    Integer selectStarsByMemberId(Integer memberId);

    /**
     * 根据关注id取消关注信息展示
     * @param id
     * @return
     */
    RespBean deleteAttentionInformation(Integer id);

    /**
     * 根据用户id取消所有关注信息展示
     * @param userId
     * @return
     */
    RespBean deleteAllAttentionInformation(Integer userId);


}
