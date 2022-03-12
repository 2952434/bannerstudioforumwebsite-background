package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.CommentBean;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.mapper.CommentMapper;
import studio.banner.forumwebsite.mapper.PostMapper;
import studio.banner.forumwebsite.service.*;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/15/17:25
 * @Description: 评论功能服务层实现
 */
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    protected CommentMapper commentMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IReplyService iReplyService;
    @Autowired
    private ICommentLikeService iCommentLikeService;
    @Autowired
    private IMemberInformationService iMemberInformationService;

    /**
     * 增加评论
     *
     * @param commentBean 评论实体
     * @return boolean
     */
    @Override
    public boolean insertComment(CommentBean commentBean) {
        if (commentBean != null) {
            commentBean.setCommentTime(TimeUtils.getDateString());

            commentMapper.insert(commentBean);
            updatePostCommentNum(commentBean.getCommentPostId());
            return true;
        }
        return false;
    }

    /**
     * 根据评论id删除该评论
     *
     * @param commentId 评论id
     * @return boolean
     */
    @Override
    public RespBean deleteComment(Integer commentId,Integer memberId) {
        CommentBean commentBean = selectComment(commentId);
        if (!commentBean.getCommentMemberId().equals(memberId)&&!commentBean.getPostMemberId().equals(memberId)){
            return RespBean.error("该评论您无权删除");
        }
        commentMapper.deleteById(commentId);
        updatePostCommentNum(commentBean.getCommentPostId());
        iReplyService.deleteAllReplyByCommentId(commentId);
        iCommentLikeService.deleteAllCommentLikeByCommentId(commentId);
        return RespBean.ok("删除成功");
    }

    /**
     * 根据帖子id删除该帖子全部评论
     *
     * @param commentPostId 被评论帖子id
     */
    @Override
    public void deleteAllCommentByPostId(Integer commentPostId) {
        UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("comment_post_id", commentPostId);
        if (commentMapper.delete(updateWrapper)==1) {
            iReplyService.deleteAllReplyByPostId(commentPostId);
            iCommentLikeService.deleteAllCommentLikeByPostId(commentPostId);
            updatePostCommentNum(commentPostId);
        }
    }

    /**
     * 根据用户id删除该用户所有评论
     *
     * @param commentMemberId 评论用户
     */
    @Override
    public void deleteAllCommentByMemberId(Integer commentMemberId) {
        UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("comment_member_id", commentMemberId);
        commentMapper.delete(updateWrapper);
        iReplyService.deleteAllReplyByMemberId(commentMemberId);
    }

    /**
     * 根据帖子id查找该帖子下全部评论
     *
     * @param commentPostId 被评论的帖子id
     * @return List<CommentBean>
     */
    @Override
    public RespBean selectAllCommentByPostId(Integer commentPostId,Integer page) {
        QueryWrapper<CommentBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_post_id", commentPostId);
        Page<CommentBean> page1 = new Page<>(page,10);
        Page<CommentBean> commentBeanPage = commentMapper.selectPage(page1, queryWrapper);
        List<CommentBean> records = commentBeanPage.getRecords();
        for (CommentBean record : records) {
            MemberInformationBean memberInformationBean = iMemberInformationService.selectUserMsg(record.getCommentMemberId());
            record.setHeadUrl(memberInformationBean.getMemberHead());
            record.setUserName(memberInformationBean.getMemberName());
        }
        return RespBean.ok("查询成功",records);
    }

    /**
     * 根据用户id查找该用户全部评论
     *
     * @param commentMemberId 用户id
     * @return List<CommentBean>
     */
    @Override
    public List<CommentBean> selectAllCommentByMemberId(Integer commentMemberId) {
        QueryWrapper<CommentBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_member_id", commentMemberId);
        if (commentMapper.selectList(queryWrapper).size() != 0) {
            return commentMapper.selectList(queryWrapper);
        }
        return null;
    }

    /**
     * 根据评论id查找该评论
     *
     * @param commentId 评论id
     * @return CommentBean
     */
    @Override
    public CommentBean selectComment(Integer commentId) {
        if (commentMapper.selectById(commentId) != null) {
            return commentMapper.selectById(commentId);
        }
        return null;
    }

    @Override
    public Integer selectCommentNum(Integer postId) {
        QueryWrapper<CommentBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_post_id",postId);
        return commentMapper.selectCount(queryWrapper);
    }

    /**
     * 分页查询全部评论
     *
     * @param page 页数
     * @return IPage<CommentBean>
     */
    @Override
    public IPage<CommentBean> selectAllComment(Integer page) {
        Page<CommentBean> page1 = new Page<>(page, 10);
        IPage<CommentBean> page2 = commentMapper.selectPage(page1, null);
        return page2;
    }

    @Override
    public void updatePostCommentNum(Integer postId) {
        UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("post_id",postId).set("post_comment_number",selectCommentNum(postId));
        postMapper.update(null,updateWrapper);
    }

    @Override
    public void updateCommentLikeNum(Integer commentId) {
        UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("comment_id",commentId).set("comment_like_number",iCommentLikeService.selectCommentLikeNum(commentId));
        commentMapper.update(null,updateWrapper);
    }

    @Override
    public void updateReplyNumByCommentId(Integer commentId) {
        UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("comment_id",commentId).set("reply_number",iReplyService.selectReplyNumByCommentId(commentId));
        commentMapper.update(null,updateWrapper);
    }

    @Override
    public List<CommentBean> selectCommentByMemberId(Integer memberId, Integer page) {
        QueryWrapper<CommentBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_member_id",memberId).eq("comment_show",0).orderByDesc("comment_time");
        Page<CommentBean> page1 = new Page<>(page,7);
        Page<CommentBean> commentBeanPage = commentMapper.selectPage(page1, queryWrapper);
        List<CommentBean> records = commentBeanPage.getRecords();
        for (CommentBean record : records) {
            PostBean postBean = iPostService.selectPost(record.getCommentPostId());
            record.setPostTitle(postBean.getPostTitle());
            MemberInformationBean memberInformationBean = iMemberInformationService.selectUserMsg(record.getCommentMemberId());
            record.setHeadUrl(memberInformationBean.getMemberHead());
            record.setUserName(memberInformationBean.getMemberName());
        }
        return records;
    }

    @Override
    public boolean deleteCommentInformationById(Integer commentId) {
        UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("comment_id",commentId).set("comment_show",1);
        return commentMapper.update(null,updateWrapper)==1;
    }

    @Override
    public boolean deleteAllCommentInformationById(Integer memberId) {
        UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("post_member_id",memberId).set("comment_show",1);
        return commentMapper.update(null,updateWrapper)==1;
    }
}
