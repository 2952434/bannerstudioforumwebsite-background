package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.PostLikeBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.mapper.PostLikeMapper;
import studio.banner.forumwebsite.service.IMemberInformationService;
import studio.banner.forumwebsite.service.IPostLikeService;
import studio.banner.forumwebsite.service.IPostService;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/10 20:07
 * @role:
 */
@Service
public class PostLikeServiceImpl implements IPostLikeService {
    @Autowired
    private PostLikeMapper postLikeMapper;
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IMemberInformationService iMemberInformationService;

    @Override
    public boolean judgePostLike(Integer postId, Integer userId) {
        QueryWrapper<PostLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_like_id",userId).eq("like_post_id",postId);
        PostLikeBean postLikeBean = postLikeMapper.selectOne(queryWrapper);
        if (postLikeBean==null){
            return true;
        }
        return false;
    }

    @Override
    public Integer selectPostLikeNum(Integer postId) {
        QueryWrapper<PostLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("like_post_id",postId);
        return postLikeMapper.selectCount(queryWrapper);
    }

    @Override
    public RespBean insertPostLike(PostLikeBean postLikeBean) {
        if (judgePostLike(postLikeBean.getLikePostId(), postLikeBean.getUserLikeId())){
            postLikeBean.setLikeShow(0);
            postLikeBean.setLikeTime(TimeUtils.getDateString());
            if (postLikeMapper.insert(postLikeBean)==1) {
                iPostService.updatePostLikeNumber(postLikeBean.getLikePostId());
                return RespBean.ok("新增点赞成功");
            }
            return RespBean.ok("新增点赞失败");
        }
        return RespBean.error("以存在点赞关系");
    }

    @Override
    public RespBean deletePostLikeById(Integer postId, Integer userId) {
        QueryWrapper<PostLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_like_id",userId).eq("like_post_id",postId);
        if (postLikeMapper.delete(queryWrapper)==1) {
            iPostService.updatePostLikeNumber(postId);
            return RespBean.ok("取消点赞成功");
        }
        return RespBean.error("取消点赞失败");
    }

    @Override
    public Integer selectPostLikeNumByUserId(Integer userId) {
        QueryWrapper<PostLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("be_user_like_id",userId).eq("like_show",0);
        return postLikeMapper.selectCount(queryWrapper);
    }

    @Override
    public RespBean selectPostLikeByUserId(Integer userId, Integer page) {
        Page<PostLikeBean> page1 = new Page<>(page,12);
        QueryWrapper<PostLikeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("be_user_like_id",userId).eq("like_show",0);
        Page<PostLikeBean> postLikeBeanPage = postLikeMapper.selectPage(page1, queryWrapper);
        List<PostLikeBean> records = postLikeBeanPage.getRecords();
        for (PostLikeBean record : records) {
            MemberInformationBean memberInformationBean = iMemberInformationService.selectUserMsg(record.getUserLikeId());
            record.setHeadUrl(memberInformationBean.getMemberHead());
            record.setUserName(memberInformationBean.getMemberName());
        }
        return RespBean.ok("查询成功",records);
    }

    @Override
    public RespBean deletePostLikeInformation(Integer likeId) {
        UpdateWrapper<PostLikeBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("like_id",likeId).set("like_show",1);
        if (postLikeMapper.update(null, updateWrapper)==1) {
            return RespBean.ok("取消展示成功");
        }
        return RespBean.error("取消展示失败");
    }

    @Override
    public RespBean deletePostLikeAllInformation(Integer userId) {
        UpdateWrapper<PostLikeBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("be_user_like_id",userId).set("like_show",1);
        if (postLikeMapper.update(null, updateWrapper)==1) {
            return RespBean.ok("取消展示成功");
        }
        return RespBean.error("取消展示失败");
    }
}
