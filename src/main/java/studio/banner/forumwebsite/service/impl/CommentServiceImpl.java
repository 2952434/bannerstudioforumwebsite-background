package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.CommentBean;
import studio.banner.forumwebsite.mapper.CommentMapper;
import studio.banner.forumwebsite.service.ICommentService;

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

    /**
     * 增加评论
     *
     * @param commentBean 评论实体
     * @return boolean
     */
    @Override
    public boolean insertComment(CommentBean commentBean) {
        if (commentBean != null) {
            commentMapper.insert(commentBean);
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
    public boolean deleteComment(int commentId) {
        if (selectComment(commentId) != null) {
            UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("comment_id", commentId);
            commentMapper.delete(updateWrapper);
            return true;
        }
        return false;
    }

    /**
     * 根据帖子id删除该帖子全部评论
     *
     * @param commentPostId 被评论帖子id
     * @return boolean
     */
    @Override
    public boolean deleteAllCommnetByPostId(int commentPostId) {
        if (selectAllCommentByPostId(commentPostId) != null) {
            UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("comment_post_id", commentPostId);
            commentMapper.delete(updateWrapper);
            return true;
        }
        return false;
    }

    /**
     * 根据用户id删除该用户所有评论
     *
     * @param commentMemberId 评论用户
     * @return boolean
     */
    @Override
    public boolean deleteAllCommentByMemberId(int commentMemberId) {
        if (selectAllCommentByMemberId(commentMemberId) != null) {
            UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("comment_member_id", commentMemberId);
            commentMapper.delete(updateWrapper);
            return true;
        }
        return false;
    }

    /**
     * 根据评论id修改评论内容
     *
     * @param commentId  评论id
     * @param newContent 新评论内容
     * @return Boolean
     */
    @Override
    public boolean updateCommentContent(int commentId, String newContent) {
        if (commentMapper.selectById(commentId) != null) {
            UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("comment_id", commentId).set("comment_content", newContent);
            commentMapper.update(null, updateWrapper);
            return true;
        }
        return false;
    }

    /**
     * 根据评论id修改评论点赞量
     *
     * @param commentId 评论id
     * @return boolean
     */
    @Override
    public boolean updateCommentLikeNumber(int commentId) {
        if (commentMapper.selectById(commentId) != null) {
            CommentBean commentBean = selectComment(commentId);
            UpdateWrapper<CommentBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("comment_id", commentId).set("comment_like_number", commentBean.getCommentLikeNumber() + 1);
            commentMapper.update(null, updateWrapper);
        }
        return false;
    }

    /**
     * 根据帖子id查找该帖子下全部评论
     *
     * @param commentPostId 被评论的帖子id
     * @return List<CommentBean>
     */
    @Override
    public List<CommentBean> selectAllCommentByPostId(int commentPostId) {
        QueryWrapper<CommentBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_post_id", commentPostId);
        if (commentMapper.selectList(queryWrapper).size() != 0) {
            return commentMapper.selectList(queryWrapper);
        }
        return null;
    }

    /**
     * 根据用户id查找该用户全部评论
     *
     * @param commentMemberId 用户id
     * @return List<CommentBean>
     */
    @Override
    public List<CommentBean> selectAllCommentByMemberId(int commentMemberId) {
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
    public CommentBean selectComment(int commentId) {
        if (commentMapper.selectById(commentId) != null) {
            return commentMapper.selectById(commentId);
        }
        return null;
    }

    /**
     * 分页查询全部评论
     *
     * @param page 页数
     * @return IPage<CommentBean>
     */
    @Override
    public IPage<CommentBean> selectAllComment(int page) {
        Page<CommentBean> page1 = new Page<>(page, 10);
        IPage<CommentBean> page2 = commentMapper.selectPage(page1, null);
        return page2;
    }
}
