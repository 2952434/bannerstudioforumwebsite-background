package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.ReplyBean;
import studio.banner.forumwebsite.mapper.ReplyMapper;
import studio.banner.forumwebsite.service.IReplyService;

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
    public boolean insertReply(ReplyBean replyBean) {
        if (replyBean != null) {
            replyMapper.insert(replyBean);
            return true;
        }
        return false;
    }

    /**
     * 根据回复id删除回复
     *
     * @param replyId 回复id
     * @return boolean
     */
    @Override
    public boolean deleteReply(Integer replyId) {
        if (replyMapper.selectById(replyId) != null) {
            replyMapper.deleteById(replyId);
            return true;
        }
        return false;
    }

    /**
     * 根据用户id删除该用户全部回复
     *
     * @param memberId 用户id
     * @return boolean
     */
    @Override
    public boolean deleteAllReplyByMemberId(Integer memberId) {
        if (selectAllReplyByMemberId(memberId, 1).getRecords().size() != 0) {
            UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("reply_member_id", memberId);
            replyMapper.delete(updateWrapper);
            return true;
        }
        return false;
    }

    /**
     * 根据评论id删除该评论下全部回复
     *
     * @param commentId 评论id
     * @return boolean
     */
    @Override
    public boolean deleteAllReplyByCommentId(Integer commentId) {

        if (selectAllReplyByCommentId(commentId, 1) != null) {
            UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("comment_id", commentId);
            replyMapper.delete(updateWrapper);
        }
        return false;
    }

    /**
     * 修改回复内容
     *
     * @param replyId    回复id
     * @param newContent 新回复内容
     * @return boolean
     */
    @Override
    public boolean updateReplyContent(Integer replyId, String newContent) {

        if (replyMapper.selectById(replyId) != null) {
            UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("reply_id", replyId).set("reply_content", newContent);
            replyMapper.update(null, updateWrapper);
            return true;
        }
        return false;
    }

    /**
     * 修改回复点赞数量
     *
     * @param replyId 回复id
     * @return boolean
     */
    @Override
    public boolean updateReplyLikeNumber(Integer replyId) {

        if (replyMapper.selectById(replyId) != null) {
            UpdateWrapper<ReplyBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("reply_id", replyId).set("reply_like_number", replyMapper.selectById(replyId).getReplyLikeNumber() + 1);
            replyMapper.update(null, updateWrapper);
            return true;
        }
        return false;
    }

    /**
     * 根据评论id分页查询该评论下全部回复
     *
     * @param commentId 评论id
     * @param page      第几页
     * @return IPage<ReplyBean>
     */
    @Override
    public IPage<ReplyBean> selectAllReplyByCommentId(Integer commentId, int page) {
        QueryWrapper<ReplyBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", commentId);
        Page<ReplyBean> page1 = new Page<>(page, 10);
        IPage<ReplyBean> page2 = replyMapper.selectPage(page1, queryWrapper);
        if (page2.getSize() != 0) {
            return page2;
        }
        return null;
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

    /**
     * 根据回复id查询
     *
     * @param replyId 回复id
     * @return ReplyBean
     */
    @Override
    public ReplyBean selectReply(Integer replyId) {
        if (replyMapper.selectById(replyId) != null) {
            return replyMapper.selectById(replyId);
        }
        return null;
    }
}
