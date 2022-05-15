package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.CommentBean;
import studio.banner.forumwebsite.bean.RespBean;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/15/17:03
 * @Description: 评论功能服务层接口
 */
public interface ICommentService {
    /**
     * 增加评论
     *
     * @param commentBean 评论实体
     * @return boolean
     */
    boolean insertComment(CommentBean commentBean);

    /**
     * 根据评论id,和删除人id删除该评论
     * @param commentId 评论id
     * @param memberId 删除人id
     * @return
     */
    RespBean deleteComment(Integer commentId,Integer memberId);

    /**
     * 根据帖子id删除该帖子全部评论
     *
     * @param commentPostId 被评论帖子id
     */
    void deleteAllCommentByPostId(Integer commentPostId);

    /**
     * 根据用户id删除该用户所有评论
     *
     * @param commentMemberId 评论用户
     */
    void deleteAllCommentByMemberId(Integer commentMemberId);

    /**
     * 根据帖子id分页查找该帖子下全部评论
     * @param commentPostId
     * @param page
     * @return
     */
    RespBean selectAllCommentByPostId(Integer commentPostId,Integer page);

    /**
     * 根据用户id查找该用户全部评论
     *
     * @param commentMemberId 用户id
     * @return List<CommentBean>
     */
    List<CommentBean> selectAllCommentByMemberId(Integer commentMemberId);

    /**
     * 根据评论id查找该评论
     *
     * @param commentId 评论id
     * @return CommentBean
     */
    CommentBean selectComment(Integer commentId);

    /**
     * 根据帖子id查询该帖子下评论数量
     * @param postId
     * @return
     */
    Integer selectCommentNum(Integer postId);

    /**
     * 根据帖子id更新帖子评论数
     * @param postId
     */
    void updatePostCommentNum(Integer postId);

    /**
     * 根据评论id更新评论点赞量
     * @param commentId
     */
    void updateCommentLikeNum(Integer commentId);

    /**
     * 根据评论id更新回复数量
     * @param commentId
     */
    void updateReplyNumByCommentId(Integer commentId);

    /**
     * 根据用户id分页查询被评论信息
     * @param memberId
     * @param page
     * @return
     */
    List<Map<String, String>> selectCommentByMemberId(Integer memberId, Integer page);

    /**
     * 根据评论id取消显示信息
     * @param commentId
     * @return
     */
    boolean deleteCommentInformationById(Integer commentId);

    /**
     * 根据用户id取消全部显示信息
     * @param memberId
     * @return
     */
    boolean deleteAllCommentInformationById(Integer memberId);
}
