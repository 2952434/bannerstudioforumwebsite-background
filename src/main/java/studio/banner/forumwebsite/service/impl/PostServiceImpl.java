package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.mapper.PostMapper;
import studio.banner.forumwebsite.service.IPostService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/08/23:18
 * @Description: 帖子操作的实现类
 */
@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    protected PostMapper postMapper;

    @Override
    public boolean insertPost(PostBean postBean) {
        if (postBean != null) {
            postMapper.insert(postBean);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean forwardPost(PostBean postBean){
        postMapper.insert(postBean);
        return false;
    }

    @Override
    public boolean deletePost(int postId) {
        if (postMapper.selectById(postId) != null) {
            postMapper.deleteById(postId);
            System.out.println("删除成功");
            return true;
        }
        System.out.println("删除失败,未查到改篇帖子");
        return false;
    }

    @Override
    public boolean deleteAllPost(int postMemberId) {
        if (selectAllPostById(postMemberId).size() != 0) {
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_member_id", postMemberId);
            postMapper.delete(updateWrapper);
            return true;
        }
        System.out.println("删除失败");
        return false;
    }

    @Override
    public boolean updatePostContent(int postId , String newContent) {
        if(selectPost(postId) != null){
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id",postId).set("post_content",newContent);
            postMapper.update(null,updateWrapper);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePostpageview(int postId) {
        if (selectPost(postId) != null){
            PostBean postBean = selectPost(postId);
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id",postId).set("post_pageview",postBean.getPostPageview()+1);
            postMapper.update(null,updateWrapper);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePostCommentNumber(int postId) {
        if (selectPost(postId) != null){
            PostBean postBean = selectPost(postId);
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id",postId).set("post_comment_number",postBean.getPostPageview()+1);
            postMapper.update(null,updateWrapper);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePostLikeNumber(int postId) {
        if (selectPost(postId) != null){
            PostBean postBean = selectPost(postId);
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id",postId).set("post_like_number",postBean.getPostLikeNumber()+1);
            postMapper.update(null,updateWrapper);
            return true;
        }
        return false;
    }

//    public boolean updatePost(int postId) {
//        if (postMapper.selectById(postId) != null) {
//            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<PostBean>();
//            updateWrapper.eq("post_id", postId).set("post_like_number", 111);
//            postMapper.update(null, updateWrapper);
//            return true;
//        }
//        return false;
//    }

    @Override
    public PostBean selectPost(int postId) {
        return postMapper.selectById(postId);
    }

    @Override
    public List<PostBean> selectAllPostById(int postMemberId) {
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_member_id", postMemberId);
        List<PostBean> list = postMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public List<PostBean> selectAllPost() {

        List<PostBean> list = postMapper.selectList(null);
        return list;
    }
}

