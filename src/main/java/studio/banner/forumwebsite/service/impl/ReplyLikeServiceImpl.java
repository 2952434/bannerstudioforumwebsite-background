package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.ReplyLikeBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.mapper.ReplyLikeMapper;
import studio.banner.forumwebsite.service.IMemberInformationService;
import studio.banner.forumwebsite.service.IReplyLikeService;
import studio.banner.forumwebsite.service.IReplyService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/11 19:35
 * @role:
 */
@Service
public class ReplyLikeServiceImpl implements IReplyLikeService {

    @Autowired
    private ReplyLikeMapper replyLikeMapper;

    @Autowired
    private IReplyService iReplyService;
    @Autowired
    private IMemberInformationService iMemberInformationService;


    @Override
    public boolean judgeReplyLike(Integer userId, Integer replyId) {
        QueryWrapper<ReplyLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("like_user_id",userId).eq("reply_id",replyId);
        return replyLikeMapper.selectCount(queryWrapper)==0;
    }

    @Override
    public RespBean insertReplyLike(ReplyLikeBean replyLikeBean) {
        if (judgeReplyLike(replyLikeBean.getLikeUserId(),replyLikeBean.getReplyId())){
            if (replyLikeMapper.insert(replyLikeBean)==1) {
                iReplyService.updateReplyLikeByReplyId(replyLikeBean.getReplyId());
                iMemberInformationService.increaseLikeNum(replyLikeBean.getBeLikeUserId());
                return RespBean.ok("点赞成功");
            }
            return RespBean.error("点赞失败");
        }
        return RespBean.error("点赞失败，已存在点赞关系");
    }

    @Override
    public RespBean deleteReplyLike(Integer userId, Integer replyId,Integer replyUserId) {
        QueryWrapper<ReplyLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("like_user_id",userId).eq("reply_id",replyId);
        if (replyLikeMapper.delete(queryWrapper)==1) {
            iReplyService.updateReplyLikeByReplyId(replyId);
            iMemberInformationService.underLikeNum(replyUserId);
            return RespBean.ok("取消点赞成功");
        }
        return RespBean.error("取消点赞失败");
    }

    @Override
    public void deleteReplyLikeByPostId(Integer postId) {
        QueryWrapper<ReplyLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        if (replyLikeMapper.delete(queryWrapper)==1) {
        }
    }

    @Override
    public void deleteAllReplyLikeByReplyId(Integer replyId) {
        QueryWrapper<ReplyLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reply_id",replyId);
        replyLikeMapper.delete(queryWrapper);
    }

    @Override
    public Integer selectReplyLikeNum(Integer replyId) {
        QueryWrapper<ReplyLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reply_id",replyId);
        return replyLikeMapper.selectCount(queryWrapper);
    }


    @Override
    public List<ReplyLikeBean> selectReplyLikeByBeLikeUserId(Integer userId, Integer page) {
        QueryWrapper<ReplyLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("be_like_user_id",userId).eq("show_information",0).orderByDesc("like_time");
        Page<ReplyLikeBean> page1 = new Page<>(page,5);
        Page<ReplyLikeBean> commentLikeBeanPage = replyLikeMapper.selectPage(page1, queryWrapper);
        List<ReplyLikeBean> records = commentLikeBeanPage.getRecords();
        for (ReplyLikeBean record : records) {
            MemberInformationBean memberInformationBean = iMemberInformationService.selectUserMsg(record.getLikeUserId());
            record.setUserName(memberInformationBean.getMemberName());
            record.setHeadUrl(memberInformationBean.getMemberHead());
        }
        return records;
    }

    @Override
    public boolean deleteReplyLikeByLikeId(Integer likeId) {
        UpdateWrapper<ReplyLikeBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("like_id",likeId).set("show",1);
        return replyLikeMapper.update(null, updateWrapper) == 1;
    }

    @Override
    public void deleteAllReplyLikeByBeLikeUserId(Integer userId) {
        UpdateWrapper<ReplyLikeBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("be_like_user_id",userId).set("show",1);
        replyLikeMapper.update(null, updateWrapper);
    }

}
