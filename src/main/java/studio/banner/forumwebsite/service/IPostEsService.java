package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.RespBean;


/**
 * @Author: Ljx
 * @Date: 2022/3/12 16:39
 * @role:
 */
public interface IPostEsService {

    /**
     * 每两分钟更新一次es中的数据
     */
    void updateEsPost();

    /**
     * 分页查询全部内容
     * @param page
     * @return
     */
    RespBean findAllWithPage(Integer page);

    /**
     * 根据标题、内容和帖子类型查询（含分页）
     * @param condition
     * @param page
     * @return
     */
    RespBean findByPostContentAndPostTitleAndPostType(String condition,Integer page);

    /**
     * 根据帖子id删除es中的数据
     * @param postId
     * @return
     */
    void deleteEsPostById(Integer postId);

    /**
     * 根据用户id删除该用户下的帖子
     * @param memberId
     */
    void deleteEsPostByMemberId(Integer memberId);

}
