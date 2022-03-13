package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.ReplyLikeBean;
import studio.banner.forumwebsite.bean.RespBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/11 19:34
 * @role:
 */
public interface IReplyLikeService {

    /**
     * 根据用户id和回复id判断是否具有点赞关系
     * @param userId
     * @param replyId
     * @return
     */
    boolean judgeReplyLike(Integer userId,Integer replyId);

    /**
     * 增加点赞
     * @param replyLikeBean
     * @return
     */
    RespBean insertReplyLike(ReplyLikeBean replyLikeBean);

    /**
     * 根据用户id，回复id，回复用户id取消点赞
     * @param userId
     * @param replyId
     * @param replyUserId
     * @return
     */
    RespBean deleteReplyLike(Integer userId,Integer replyId,Integer replyUserId);

    /**
     * 通过帖子id删除全部点赞
     * @param postId
     */
    void deleteReplyLikeByPostId(Integer postId);

    /**
     * 根据回复id删除该评论下所有点赞信息
     * @param replyId
     */
    void deleteAllReplyLikeByReplyId(Integer replyId);

    /**
     * 根据回复id查询该回复的点赞量
     * @param replyId
     * @return
     */
    Integer selectReplyLikeNum(Integer replyId);


    /**
     * 根据用户id查询被点赞信息
     * @param userId
     * @param page
     * @return
     */
    List<ReplyLikeBean> selectReplyLikeByBeLikeUserId(Integer userId, Integer page);

    /**
     * 根据点赞id删除点赞信息
     * @param likeId
     * @return
     */
    boolean deleteReplyLikeByLikeId(Integer likeId);

    /**
     * 根据用户id删除所有点赞信息
     * @param userId
     */
    void deleteAllReplyLikeByBeLikeUserId(Integer userId);

}
