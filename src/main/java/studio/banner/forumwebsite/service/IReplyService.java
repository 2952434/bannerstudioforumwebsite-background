package studio.banner.forumwebsite.service;
import studio.banner.forumwebsite.bean.ReplyBean;

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
     * @param replyBean
     * @return boolean
     */
    boolean insertReply(ReplyBean replyBean);

    /**
     * 根据回复id删除回复
     * @param replyId
     * @return boolean
     */
    boolean deleteReply(Integer replyId);

    /**
     * 根据用户id删除该用户全部回复
     * @param memberId
     * @return boolean
     */
    boolean deleteAllReplyByMemberId(Integer memberId);

    /**
     * 根据评论id删除该评论下全部回复
     * @param commentId
     * @return boolean
     */
    boolean deleteAllReplyByCommentId(Integer commentId);

    /**
     * 修改回复内容
     * @param replyId
     * @return boolean
     */
    boolean updateReplyContent(Integer replyId, String newContent);

    /**
     * 修改回复点赞数量
     * @param replyId
     * @return boolean
     */
    boolean updateReplyLikeNumber(Integer replyId);

    /**
     * 根据评论id查询该评论下全部回复
     * @param commenmtId
     * @return List
     */
    List<ReplyBean> selectAllReplyByCommentId(Integer commenmtId);

    /**
     * 根据用户id查询该用户下全部回复
     * @param memberId
     * @return List
     */
    List<ReplyBean> selectAllReplyByMemberId(Integer memberId);

    /**
     * 根据回复id查询
     * @param replyId
     * @return ReplyBean
     */
    ReplyBean selectReply(Integer replyId);

}
