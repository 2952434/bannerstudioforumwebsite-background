package studio.banner.forumwebsite.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.mapper.PostMapper;
import studio.banner.forumwebsite.service.IPostService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public static final String POST_RANK = "post_rank";

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
        return true;
    }

    @Override
    public boolean deletePost(int postId) {
        if (postMapper.selectById(postId) != null) {
            PostBean postBean = selectPost(postId);
            String key = postBean.getPostTitle()+","+postBean.getPostId();
            redisTemplate.opsForZSet().remove(POST_RANK,key);
            postMapper.deleteById(postId);
            return true;
        }
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
    public boolean updatePostTitle(int postId ,String newTitle) {
        if(selectPost(postId) != null){
            PostBean postBean = selectPost(postId);
            String key = postBean.getPostTitle()+","+postBean.getPostId();
            redisTemplate.opsForZSet().remove(POST_RANK,key);
            String newKey = newTitle+","+postBean.getPostId();
            redisTemplate.opsForZSet().add(POST_RANK,newKey,postBean.getPostPageView());
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id",postId).set("post_title", newTitle);
            postMapper.update(null,updateWrapper);
            return true;
        }
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
            String key = postBean.getPostTitle()+","+postBean.getPostId();
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            redisTemplate.opsForZSet().incrementScore(POST_RANK,key,1);
            updateWrapper.eq("post_id",postId).set("post_page_view",postBean.getPostPageView()+1);
            postMapper.update(null,updateWrapper);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePostCommentNumber(int postId) {
        if (selectPost(postId) != null){
            PostBean postBean = selectPost(postId);
            System.out.println(postBean);
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id",postId).set("post_comment_number",postBean.getPostCommentNumber()+1);
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


    @Override
    public PostBean selectPost(int postId) {
        return postMapper.selectById(postId);
    }

    @Override
    public List<PostBean> selectAllPostById(int postMemberId) {
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_member_id", postMemberId);
        List<PostBean> list = postMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            return list;
        }
        return null;
    }

    @Override
    public IPage<PostBean> selectAllPost(int page) {
        Page<PostBean> page1 = new Page<>(page,10);
        IPage<PostBean> page2 = postMapper.selectPage(page1,null);
        if (page2.getSize() != 0) {
            return page2;
        }
        return null;
    }

    @Override
    public IPage<PostBean> selectDimPost(int page, String dim) {
        Page<PostBean> Page01 = new Page<>(page, 10);
        QueryWrapper<PostBean> query = new QueryWrapper<>();
        query.like("post_title",dim)
                .or()
                .like("post_content",dim);
        IPage<PostBean> page1 =postMapper.selectPage(Page01,query);
        return page1;
    }


//    @Override
//    public Set<ZSetOperations.TypedTuple<String>> addRedis() {
//        redisTemplate.opsForZSet().removeRangeByScore(POST_RANK,0,1000000);
//        QueryWrapper<PostBean> queryWrapper = new QueryWrapper();
//        queryWrapper.select("post_id","post_title","post_page_view");
//        List<PostBean> list = postMapper.selectList(queryWrapper);
//        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
//        if(CollectionUtils.isNotEmpty(list)){
//            for (PostBean postBean: list) {
//                String key = postBean.getPostTitle()+","+postBean.getPostId();
//                DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(key, (double) postBean.getPostPageView());
//                tuples.add(tuple);
//            }
//        }
//        redisTemplate.opsForZSet().add(POST_RANK, tuples);
//        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 10);
//        return rangeWithScores;
//    }

    @Override
    @Scheduled(cron="0 0 1 * * ?")
    public void updateRedisPostRank() {
        redisTemplate.opsForZSet().removeRangeByScore(POST_RANK,0,1000000);
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper();
        queryWrapper.select("post_id","post_title","post_page_view");
        List<PostBean> list = postMapper.selectList(queryWrapper);
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        if(CollectionUtils.isNotEmpty(list)){
            for (PostBean postBean: list) {
                String key = postBean.getPostTitle()+","+postBean.getPostId();
                DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(key, (double) postBean.getPostPageView());
                tuples.add(tuple);
            }
        }
        redisTemplate.opsForZSet().add(POST_RANK, tuples);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> selectPostRank() {
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 10);
        return rangeWithScores;
    }

}

