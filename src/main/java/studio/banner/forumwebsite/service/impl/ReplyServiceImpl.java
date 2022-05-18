package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.mapper.ReplyMapper;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IMemberInformationService;
import studio.banner.forumwebsite.service.IReplyLikeService;
import studio.banner.forumwebsite.service.IReplyService;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/17/18:07
 * @Description: 回复操作的实现类
 */
@Service
public class ReplyServiceImpl implements IReplyService {

    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IReplyLikeService iReplyLikeService;
    @Autowired
    private IMemberInformationService iMemberInformationService;

    /**
     * 添加回复
     *
     * @param replyBean 回复实体
     * @return boolean
     */
    @Override
    public RespBean insertReply(ReplyBean replyBean) {
        if (replyBean != null) {
            replyBean.setReplyTime(TimeUtils.getDateString());
            if (replyMapper.insert(replyBean)==1) {
                iCommentService.updateReplyNumByCommentId(replyBean.getCommentId());
                return RespBean.ok("回复评论成功");
            }
            return RespBean.error("回复评论失败");
        }
        return RespBean.error("回复评论为空");
    }

    /**
     * 根据回复id删除回复
     *
     * @param replyId 回复id
     * @return boolean
     */
    @Override
    public RespBean deleteReply(Integer replyId,Integer memberId) {
        ReplyBean replyBean = replyMapper.selectById(replyId);
        if (replyBean.getPostMemberId().equals(memberId)||replyBean.getReplyMemberId().equals(memberId)||replyBean.getReplyCommentMemberId().equals(memberId)){
            if (replyMapper.deleteById(replyId)==1) {
                iReplyLikeService.deleteAllReplyLikeByReplyId(replyId);
                iCommentService.updateReplyNumByCommentId(replyBean.getCommentId());
                return RespBean.ok("删除成功");
            }
            return RespBean.error("删除失败");
        }
        return RespBean.error("您没有权限删除该回复内容");
    }

    /**
     * 根据用户id删除该用户全部回复
     *
     * @param memberId 用户id
     * @return boolean
     */
    @Override
    public RespBean deleteAllReplyByMemberId(Integer memberId) {
        UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reply_member_id", memberId);
        if (replyMapper.delete(updateWrapper)==1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @Override
    public void deleteAllReplyByPostId(Integer postId) {
        UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("post_id",postId);
        replyMapper.delete(updateWrapper);
        iReplyLikeService.deleteReplyLikeByPostId(postId);
    }

    /**
     * 根据评论id删除该评论下全部回复
     *
     * @param commentId 评论id
     * @return boolean
     */
    @Override
    public RespBean deleteAllReplyByCommentId(Integer commentId) {
        UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("comment_id", commentId);
        if (replyMapper.delete(updateWrapper)==1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @Override
    public void updateReplyLikeByReplyId(Integer replyId) {
        UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reply_id",replyId).set("reply_like_number",iReplyLikeService.selectReplyLikeNum(replyId));
        replyMapper.update(null,updateWrapper);
    }

    @Override
    public Integer selectReplyNumByCommentId(Integer commentId) {
        QueryWrapper<ReplyBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id",commentId);
        return replyMapper.selectCount(queryWrapper);
    }

    /**
     * 根据评论id分页查询该评论下全部回复
     *
     * @param commentId 评论id
     * @return IPage<ReplyBean>
     */
    @Override
    public RespBean selectAllReplyByCommentId(Integer commentId,Integer page) {
        List<Map<String, String>> maps = replyMapper.selectAllReplyByCommentId(commentId, (page - 1) * 5);
        return RespBean.ok("查询成功",maps);
    }



    @Override
    public List<Map<String, String>> selectReplyInformationById(Integer memberId, Integer page) {
        return replyMapper.selectReplyInformationById(memberId,(page-1)*15);
    }

    @Override
    public boolean deleteReplyInformationById(Integer replyId) {
        UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reply_id",replyId).set("reply_show",1);
        return replyMapper.update(null,updateWrapper)==1;
    }

    @Override
    public boolean deleteAllReplyInformationById(Integer memberId) {
        UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reply_comment_member_id",memberId).set("reply_show",1);
        return replyMapper.update(null, updateWrapper)==1;
    }

}
