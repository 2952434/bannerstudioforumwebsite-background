package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.CommentLikeBean;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.mapper.CommentLikeMapper;
import studio.banner.forumwebsite.service.ICommentLikeService;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IMemberInformationService;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/11 15:12
 * @role:
 */
@Service
public class CommentLikeServiceImpl implements ICommentLikeService {

    @Autowired
    private CommentLikeMapper commentLikeMapper;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IMemberInformationService iMemberInformationService;

    @Override
    public boolean judgeCommentLike(Integer userId, Integer commentId) {
        QueryWrapper<CommentLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("like_user_id",userId).eq("comment_id",commentId);
        return commentLikeMapper.selectCount(queryWrapper) == 0;
    }

    @Override
    public RespBean insertCommentLike(CommentLikeBean commentLikeBean) {
        if (judgeCommentLike(commentLikeBean.getLikeUserId(),commentLikeBean.getCommentId())){
            commentLikeBean.setLikeTime(TimeUtils.getDateString());
            commentLikeMapper.insert(commentLikeBean);
            iCommentService.updateCommentLikeNum(commentLikeBean.getCommentId());
            return RespBean.ok("点赞成功");
        }
        return RespBean.error("以存在点赞关系");
    }

    @Override
    public RespBean deleteCommentLike(Integer userId, Integer commentId) {
        QueryWrapper<CommentLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("like_user_id",userId).eq("comment_id",commentId);
        if (commentLikeMapper.delete(queryWrapper)==1) {
            iCommentService.updateCommentLikeNum(commentId);
            return RespBean.ok("取消点赞成功");
        }
        return RespBean.error("取消点赞失败");
    }

    @Override
    public void deleteAllCommentLikeByCommentId(Integer commentId) {
        QueryWrapper<CommentLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id",commentId);
        commentLikeMapper.delete(queryWrapper);
    }

    @Override
    public void deleteAllCommentLikeByPostId(Integer postId) {
        QueryWrapper<CommentLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        commentLikeMapper.delete(queryWrapper);
    }

    @Override
    public Integer selectCommentLikeNum(Integer commentId) {
        QueryWrapper<CommentLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id",commentId);
        return commentLikeMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer selectUserLikeNum(Integer beLikeUserId) {
        QueryWrapper<CommentLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("be_like_user_id",beLikeUserId);
        return commentLikeMapper.selectCount(queryWrapper);
    }

    @Override
    public List<CommentLikeBean> selectCommentLikeByBeLikeUserId(Integer userId, Integer page) {
        QueryWrapper<CommentLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("be_like_user_id",userId).eq("show",0).orderByDesc("like_time");
        Page<CommentLikeBean> page1 = new Page<>(page,5);
        Page<CommentLikeBean> commentLikeBeanPage = commentLikeMapper.selectPage(page1, queryWrapper);
        List<CommentLikeBean> records = commentLikeBeanPage.getRecords();
        for (CommentLikeBean record : records) {
            MemberInformationBean memberInformationBean = iMemberInformationService.selectUserMsg(record.getLikeUserId());
            record.setUserName(memberInformationBean.getMemberName());
            record.setHeadUrl(memberInformationBean.getMemberHead());
        }
        return records;
    }

    @Override
    public boolean deleteCommentLikeByLikeId(Integer likeId) {
        UpdateWrapper<CommentLikeBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("like_id",likeId).set("show",1);
        return commentLikeMapper.update(null, updateWrapper) == 1;
    }

    @Override
    public boolean deleteAllCommentLikeByBeLikeUserId(Integer userId) {
        UpdateWrapper<CommentLikeBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("be_like_user_id",userId).set("show",1);
        return commentLikeMapper.update(null, updateWrapper) == 1;
    }

}
