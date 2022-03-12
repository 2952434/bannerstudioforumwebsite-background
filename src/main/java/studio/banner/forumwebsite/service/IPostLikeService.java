package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.PostLikeBean;
import studio.banner.forumwebsite.bean.RespBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/10 20:04
 * @role:
 */
public interface IPostLikeService {


    /**
     * 根据帖子id和用户id判断是否具有点赞关系
     * @param postId
     * @param userId
     * @return
     */
    boolean judgePostLike(Integer postId,Integer userId);

    /**
     * 根据帖子id查询该帖子的点赞量
     * @param postId
     * @return
     */
    Integer selectPostLikeNum(Integer postId);
    /**
     * 增加帖子点赞
     * @param postLikeBean
     * @return
     */
    RespBean insertPostLike(PostLikeBean postLikeBean);

    /**
     * 根据帖子id和用户id取消点赞
     * @param postId
     * @param userId
     * @return
     */
    RespBean deletePostLikeById(Integer postId,Integer userId);

    /**
     * 根据用户id查询被点赞数量
     * @param userId
     * @return
     */
    Integer selectPostLikeNumByUserId(Integer userId);

    /**、
     * 通过用户id分页查询点赞信息
     * @param userId
     * @param page
     * @return
     */
    List<PostLikeBean> selectPostLikeByUserId(Integer userId, Integer page);

    /**
     * 根据点赞id取消显示
     * @param likeId
     * @return
     */
    boolean deletePostLikeInformation(Integer likeId);

    /**
     * 通过用户id取消点赞信息全部展示
     * @param userId
     * @return
     */
    boolean deletePostLikeAllInformation(Integer userId);
}
