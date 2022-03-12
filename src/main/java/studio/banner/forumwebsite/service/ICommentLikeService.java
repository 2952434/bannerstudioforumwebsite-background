package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.CommentLikeBean;
import studio.banner.forumwebsite.bean.RespBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/11 15:09
 * @role:
 */
public interface ICommentLikeService {

    /**
     * 根据用户id和评论id判断是否具有点赞关系
     * @param userId
     * @param commentId
     * @return
     */
    boolean judgeCommentLike(Integer userId,Integer commentId);

    /**
     * 增加点赞
     * @param commentLikeBean
     * @return
     */
    RespBean insertCommentLike(CommentLikeBean commentLikeBean);

    /**
     * 根据用户id和评论id取消点赞
     * @param userId
     * @param commentId
     * @return
     */
    RespBean deleteCommentLike(Integer userId,Integer commentId);

    /**
     * 根据评论id删除该评论下所有点赞信息
     * @param commentId
     */
    void deleteAllCommentLikeByCommentId(Integer commentId);

    /**
     * 根据帖子id删除所有点赞信息
     * @param postId
     */
    void deleteAllCommentLikeByPostId(Integer postId);

    /**
     * 根据评论id查询该评论的点赞量
     * @param commentId
     * @return
     */
    Integer selectCommentLikeNum(Integer commentId);

    /**
     * 通过被点赞者id查询用户被点赞量
     * @param beLikeUserId
     * @return
     */
    Integer selectUserLikeNum(Integer beLikeUserId);

    /**
     * 根据用户id查询被点赞信息
     * @param userId
     * @param page
     * @return
     */
    List<CommentLikeBean> selectCommentLikeByBeLikeUserId(Integer userId,Integer page);

    /**
     * 根据点赞id删除点赞信息
     * @param likeId
     * @return
     */
    boolean deleteCommentLikeByLikeId(Integer likeId);

    /**
     * 根据用户id删除所有点赞信息
     * @param userId
     * @return
     */
    boolean deleteAllCommentLikeByBeLikeUserId(Integer userId);
}
