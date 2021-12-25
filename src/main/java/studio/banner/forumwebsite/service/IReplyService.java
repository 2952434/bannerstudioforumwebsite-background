package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
     *
     * @param replyBean 回复实体
     * @return boolean
     */
    boolean insertReply(ReplyBean replyBean);

    /**
     * 根据回复id删除回复
     *
     * @param replyId 回复id
     * @return boolean
     */
    boolean deleteReply(Integer replyId);

    /**
     * 根据用户id删除该用户全部回复
     *
     * @param memberId 用户id
     * @return boolean
     */
    boolean deleteAllReplyByMemberId(Integer memberId);

    /**
     * 根据评论id删除该评论下全部回复
     *
     * @param commentId 评论id
     * @return boolean
     */
    boolean deleteAllReplyByCommentId(Integer commentId);

    /**
     * 修改回复内容
     *
     * @param replyId    回复id
     * @param newContent 新回复内容
     * @return boolean
     */
    boolean updateReplyContent(Integer replyId, String newContent);

    /**
     * 修改回复点赞数量
     *
     * @param replyId 回复id
     * @return boolean
     */
    boolean updateReplyLikeNumber(Integer replyId);

    /**
     * 根据评论id分页查询该评论下全部回复
     *
     * @param commentId 评论id
     * @param page      第几页
     * @return IPage<ReplyBean>
     */
    IPage<ReplyBean> selectAllReplyByCommentId(Integer commentId, int page);

    /**
     * 根据用户分页查询该用户下全部回复
     *
     * @param memberId 用户id
     * @param page     第几页
     * @return IPage<ReplyBean>
     */
    IPage<ReplyBean> selectAllReplyByMemberId(Integer memberId, int page);

    /**
     * 根据回复id查询
     *
     * @param replyId 回复id
     * @return ReplyBean
     */
    ReplyBean selectReply(Integer replyId);

}
