package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
     * 根据评论id查询该评论下全部回复
     *
     * @param commentId 评论id
     * @return IPage<ReplyBean>
     */
    RespBean selectAllReplyByCommentId(Integer commentId);

    /**
     * 根据用户分页查询该用户下全部回复
     *
     * @param memberId 用户id
     * @param page     第几页
     * @return IPage<ReplyBean>
     */
    IPage<ReplyBean> selectAllReplyByMemberId(Integer memberId, int page);

    /**
     * 通过被回复人id查询回复
     * @param beReplyMemberId
     * @return
     */
    RespBean selectReplyByBeReplyMemberId(Integer beReplyMemberId);


}
