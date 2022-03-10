package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.ReplyBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.mapper.ReplyMapper;
import studio.banner.forumwebsite.service.IReplyService;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.List;

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
    ReplyMapper replyMapper;

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

    /**
     * 根据评论id分页查询该评论下全部回复
     *
     * @param commentId 评论id
     * @return IPage<ReplyBean>
     */
    @Override
    public RespBean selectAllReplyByCommentId(Integer commentId) {
        QueryWrapper<ReplyBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", commentId);
        List<ReplyBean> replyBeans = replyMapper.selectList(queryWrapper);
        if (replyBeans.size()==0){
            return RespBean.error("该评论下无回复");
        }
        return RespBean.ok("查询成功",replyBeans);
    }

    /**
     * 根据用户分页查询该用户下全部回复
     *
     * @param memberId 用户id
     * @param page     第几页
     * @return IPage<ReplyBean>
     */
    @Override
    public IPage<ReplyBean> selectAllReplyByMemberId(Integer memberId, int page) {
        QueryWrapper<ReplyBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reply_member_id", memberId);
        Page<ReplyBean> page1 = new Page<>(page, 10);
        IPage<ReplyBean> page2 = replyMapper.selectPage(page1, queryWrapper);
        if (page2.getSize() != 0) {
            return page2;
        }
        return null;
    }

    @Override
    public RespBean selectReplyByBeReplyMemberId(Integer beReplyMemberId) {
        QueryWrapper<ReplyBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reply_comment_member_id",beReplyMemberId);
        List<ReplyBean> replyBeans = replyMapper.selectList(queryWrapper);
        if (replyBeans.size()==0){
            return RespBean.error("该用户下无用户回复");
        }
        return RespBean.ok("查询成功",replyBeans);
    }
}
