package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.ReplyBean;
import studio.banner.forumwebsite.bean.RespBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/17/17:45
 * @Description: 回复操作的接口
 */
public interface IReplyService {
    /**
     * 添加回复
     *
     * @param replyBean 回复实体
     * @return boolean
     */
    RespBean insertReply(ReplyBean replyBean);

    /**
     * 根据回复id和要删除的用户id删除回复
     * @param replyId
     * @param memberId
     * @return
     */
    RespBean deleteReply(Integer replyId,Integer memberId);

    /**
     * 根据用户id删除该用户全部回复
     *
     * @param memberId 用户id
     * @return boolean
     */
    RespBean deleteAllReplyByMemberId(Integer memberId);

    /**
     * 通过帖子id删除该帖子下的全部回复
     * @param postId
     */
    void deleteAllReplyByPostId(Integer postId);

    /**
     * 根据评论id删除该评论下全部回复
     *
     * @param commentId 评论id
     * @return boolean
     */
    RespBean deleteAllReplyByCommentId(Integer commentId);

    /**
     * 根据回复id更新回复点赞数量
     * @param replyId
     */
    void updateReplyLikeByReplyId(Integer replyId);

    /**
     * 根据评论id查询回复数量
     * @param commentId
     * @return
     */
    Integer selectReplyNumByCommentId(Integer commentId);

    /**
     * 根据评论id查询该评论下全部回复
     * @param commentId
     * @param page
     * @return
     */
    RespBean selectAllReplyByCommentId(Integer commentId,Integer page);

    /**
     * 根据用户id分页查询回复信息
     * @param memberId
     * @param page
     * @return
     */
    List<ReplyBean> selectReplyInformationById(Integer memberId,Integer page);

    /**
     * 通过评论id取消该评论展示
     * @param replyId
     * @return
     */
    boolean deleteReplyInformationById(Integer replyId);

    /**
     * 通过用户id取消全部评论展示
     * @param memberId
     */
    void deleteAllReplyInformationById(Integer memberId);
}
