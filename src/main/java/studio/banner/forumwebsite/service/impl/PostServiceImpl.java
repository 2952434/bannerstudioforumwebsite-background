package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.mapper.*;
import studio.banner.forumwebsite.service.IPostService;

import java.util.Arrays;
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
    private PostMapperEs postMapperEs;

    @Autowired
    private PostTypeMapper postTypeMapper;

    @Autowired
    private PostContactMapper postContactMapper;

    @Autowired
    private UserGradeMapper userGradeMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String POST_RANK = "post_rank";

    /**
     * 增加帖子
     *
     * @param postBean 帖子实体
     * @param postType 帖子类型
     * @return boolean
     */
    @Override
    public boolean insertPost(PostBean postBean,String postGrade, String... postType) {
        if (postBean != null) {
            postMapper.insert(postBean);
            QueryWrapper<PostBean> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("post_title", postBean.getPostTitle())
                    .eq("post_time", postBean.getPostTime());
            List<PostBean> list = postMapper.selectList(queryWrapper);
            Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
            for (PostBean postBean1 : list) {
                for (String s : postType) {
                    QueryWrapper<PostTypeBean> typeBeanQueryWrapper = new QueryWrapper<>();
                    typeBeanQueryWrapper.eq("post_type", s);
                    PostTypeBean postTypeBean = postTypeMapper.selectOne(typeBeanQueryWrapper);
                    QueryWrapper<UserGradeBean> gradeBean = new QueryWrapper<>();
                    gradeBean.eq("user_grade",postGrade);
                    UserGradeBean postGradeBean = userGradeMapper.selectOne(gradeBean);
                    PostContactBean postContactBean = new PostContactBean();
                    postContactBean.setPostId(postBean1.getPostId());
                    postContactBean.setPostTypeId(postTypeBean.getId());
                    postContactBean.setPostGradeId(postGradeBean.getId());
                    postContactMapper.insert(postContactBean);
                }
                PostBeanEs postBeanEs = new PostBeanEs();
                postBeanEs.setId(postBean1.getPostId());
                postBeanEs.setTitle(postBean1.getPostTitle());
                postBeanEs.setContext(postBean1.getPostContent());
                postBeanEs.setHits(postBean1.getPostPageView());
                postBeanEs.setTime(postBean1.getPostTime());
                postBeanEs.setCommentNumber(postBean1.getPostCommentNumber());
                postBeanEs.setLikeNumber(postBean1.getPostLikeNumber());
                postBeanEs.setPostImageAddress(postBean1.getPostImageAddress());
                postBeanEs.setForward(postBean1.getPostForward());
                postBeanEs.setUid(postBean1.getPostMemberId());
                postMapperEs.save(postBeanEs);
                String key = postBean1.getPostTitle() + "," + postBean1.getPostId();
                DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(key, (double) postBean1.getPostPageView());
                tuples.add(tuple);
            }
            redisTemplate.opsForZSet().add(POST_RANK, tuples);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据帖子id删除帖子
     *
     * @param postId 帖子id
     * @return boolean
     */
    @Override
    public boolean deletePost(int postId) {
        if (postMapper.selectById(postId) != null) {
            PostBean postBean = selectPost(postId);
            String key = postBean.getPostTitle() + "," + postBean.getPostId();
            redisTemplate.opsForZSet().remove(POST_RANK, key);
            postMapperEs.deleteById(postId);
            postMapper.deleteById(postId);
            return true;
        }
        return false;
    }

    /**
     * 根据用户id删除用户全部帖子
     *
     * @param postMemberId 用户id
     * @return boolean
     */
    @Override
    public boolean deleteAllPost(int postMemberId) {
        if (selectAllPostByDescById(postMemberId).size() != 0) {
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_member_id", postMemberId);
            List<PostBean> list = postMapper.selectList(updateWrapper);
            for (PostBean postBean : list) {
                String key = postBean.getPostTitle() + "," + postBean.getPostId();
                redisTemplate.opsForZSet().remove(POST_RANK, key);
                postMapperEs.deleteById(postBean.getPostId());
            }
            postMapper.delete(updateWrapper);
            return true;
        }
        System.out.println("删除失败");
        return false;
    }

    /**
     * 根据帖子id更改帖子标题
     *
     * @param postId   帖子id
     * @param newTitle 新标题
     * @return boolean
     */
    @Override
    public boolean updatePostTitle(int postId, String newTitle) {
        if (selectPost(postId) != null) {
            PostBean postBean = selectPost(postId);
            String key = postBean.getPostTitle() + "," + postBean.getPostId();
            redisTemplate.opsForZSet().remove(POST_RANK, key);
            String newKey = newTitle + "," + postBean.getPostId();
            redisTemplate.opsForZSet().add(POST_RANK, newKey, postBean.getPostPageView());
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id", postId).set("post_title", newTitle);
            postMapper.update(null, updateWrapper);

            PostBeanEs postBeanEs = new PostBeanEs();
            postBeanEs.setId(postBean.getPostId());
            postBeanEs.setTitle(newTitle);
            postBeanEs.setContext(postBean.getPostContent());
            postBeanEs.setHits(postBean.getPostPageView());
            postBeanEs.setTime(postBean.getPostTime());
            postBeanEs.setCommentNumber(postBean.getPostCommentNumber());
            postBeanEs.setLikeNumber(postBean.getPostLikeNumber());
            postBeanEs.setPostImageAddress(postBean.getPostImageAddress());
            postBeanEs.setForward(postBean.getPostForward());
            postBeanEs.setUid(postBean.getPostMemberId());
            postMapperEs.save(postBeanEs);
            return true;
        }
        return false;
    }


    /**
     * 根据帖子id更改帖子内容
     *
     * @param postId     帖子id
     * @param newContent 新内容
     * @return boolean
     */
    @Override
    public boolean updatePostContent(int postId, String newContent) {
        if (selectPost(postId) != null) {
            PostBean postBean = selectPost(postId);
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id", postId).set("post_content", newContent);

            postMapper.update(null, updateWrapper);
            PostBeanEs postBeanEs = new PostBeanEs();
            postBeanEs.setId(postBean.getPostId());
            postBeanEs.setTitle(postBean.getPostTitle());
            postBeanEs.setContext(newContent);
            postBeanEs.setHits(postBean.getPostPageView());
            postBeanEs.setTime(postBean.getPostTime());
            postBeanEs.setCommentNumber(postBean.getPostCommentNumber());
            postBeanEs.setLikeNumber(postBean.getPostLikeNumber());
            postBeanEs.setPostImageAddress(postBean.getPostImageAddress());
            postBeanEs.setForward(postBean.getPostForward());
            postBeanEs.setUid(postBean.getPostMemberId());
            postMapperEs.save(postBeanEs);
            return true;
        }
        return false;
    }

    /**
     * 根据帖子id更改浏览量
     *
     * @param postId 帖子id
     * @return boolean
     */
    @Override
    public boolean updatePostPageView(int postId) {
        if (selectPost(postId) != null) {
            PostBean postBean = selectPost(postId);
            String key = postBean.getPostTitle() + "," + postBean.getPostId();
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            redisTemplate.opsForZSet().incrementScore(POST_RANK, key, 1);
            Integer view = (Integer) redisTemplate.opsForHash().get(String.valueOf(postBean.getPostMemberId()), "view");
            if (view == null) {
                view = 0;
            }
            redisTemplate.opsForHash().put(String.valueOf(postBean.getPostMemberId()), "view", String.valueOf(view + 1));
            updateWrapper.eq("post_id", postId).set("post_page_view", postBean.getPostPageView() + 1);
            postMapper.update(null, updateWrapper);
            return true;
        }
        return false;
    }

    /**
     * 根据帖子id更改评论量
     *
     * @param postId 帖子id
     * @return boolean
     */
    @Override
    public boolean updatePostCommentNumber(int postId) {
        if (selectPost(postId) != null) {
            PostBean postBean = selectPost(postId);
            System.out.println(postBean);
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id", postId).set("post_comment_number", postBean.getPostCommentNumber() + 1);
            postMapper.update(null, updateWrapper);
            return true;
        }
        return false;
    }

    /**
     * 根据帖子id更改点赞量
     *
     * @param postId 帖子id
     * @return boolean
     */
    @Override
    public boolean updatePostLikeNumber(int postId) {
        if (selectPost(postId) != null) {
            PostBean postBean = selectPost(postId);
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id", postId).set("post_like_number", postBean.getPostLikeNumber() + 1);
            postMapper.update(null, updateWrapper);
            return true;
        }
        return false;
    }


    /**
     * 根据帖子id查询帖子
     *
     * @param postId 帖子id
     * @return PostBean
     */
    @Override
    public PostBean selectPost(int postId) {
        return postMapper.selectById(postId);
    }

    /**
     * 根据用户id查询某用户全部帖子(根据时间返向排序)
     *
     * @param postMemberId 用户id
     * @return List
     */
    @Override
    public List<PostBean> selectAllPostByDescById(int postMemberId) {
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_member_id", postMemberId)
                .orderByDesc("post_top", "post_time");
        List<PostBean> list = postMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            return list;
        }
        return null;
    }

    /**
     * 根据用户id查询某用户全部帖子(根据时间正向排序)
     *
     * @param postMemberId 用户id
     * @return List
     */
    @Override
    public List<PostBean> selectAllPostByAscById(int postMemberId) {
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_member_id", postMemberId)
                .orderByDesc("post_top")
                .orderByAsc("post_time");
        List<PostBean> list = postMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            return list;
        }
        return null;
    }

    /**
     * 分页查询所有帖子
     *
     * @param page 页数
     * @return IPage
     */
    @Override
    public IPage<PostBean> selectAllPost(int page) {
        Page<PostBean> page1 = new Page<>(page, 10);
        IPage<PostBean> page2 = postMapper.selectPage(page1, null);
        if (page2.getSize() != 0) {
            return page2;
        }
        return null;
    }

    /**
     * 全文检索帖子和作者
     *
     * @param page 第几页
     * @param dim  查询字段
     * @return List<PostBeanEs>
     */
    @Override
    public List<PostBeanEs> selectDimPost(int page, String dim) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        List<PostBeanEs> postBeanList = postMapperEs.findByTitleOrContext(dim, dim, pageRequest);
        return postBeanList;
    }

    /**
     * 将数据库中的帖子导入到Redis中
     *
     * @return Set<ZSetOperations.TypedTuple < String>>
     */
    @Override
    public Set<ZSetOperations.TypedTuple<String>> addRedis() {
        redisTemplate.opsForZSet().removeRangeByScore(POST_RANK, 0, 1000000);
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper();
        queryWrapper.select("post_id", "post_title", "post_page_view");
        List<PostBean> list = postMapper.selectList(queryWrapper);
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (PostBean postBean : list) {
                String key = postBean.getPostTitle() + "," + postBean.getPostId();
                DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(key, (double) postBean.getPostPageView());
                tuples.add(tuple);
            }
        }
        Long add = redisTemplate.opsForZSet().add(POST_RANK, tuples);
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 10);
        return rangeWithScores;
    }

    /**
     * 每天早上1点自动更新Redis数据库中的帖子排名
     *
     * @return Set<ZSetOperations.TypedTuple < String>>
     */
    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public Set<ZSetOperations.TypedTuple<String>> updateRedisPostRank() {
        redisTemplate.opsForZSet().removeRangeByScore(POST_RANK, 0, 1000000);
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper();
        queryWrapper.select("post_id", "post_title", "post_page_view");
        List<PostBean> list = postMapper.selectList(queryWrapper);
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (PostBean postBean : list) {
                String key = postBean.getPostTitle() + "," + postBean.getPostId();
                DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(key, (double) postBean.getPostPageView());
                tuples.add(tuple);
            }
        }
        Long add = redisTemplate.opsForZSet().add(POST_RANK, tuples);
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 10);
        return rangeWithScores;
    }

    /**
     * 帖子排行榜查询
     *
     * @return Set<ZSetOperations.TypedTuple < String>>
     */
    @Override
    public Set<ZSetOperations.TypedTuple<String>> selectPostRank() {
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 10);
        return rangeWithScores;
    }

    /**
     * 每分钟更新一次es中的数据
     */
    @Override
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateEsPost() {
        List<PostBean> list = postMapper.selectList(null);
        for (PostBean postBean : list) {
            PostBeanEs postBeanEs = new PostBeanEs();
            postBeanEs.setId(postBean.getPostId());
            postBeanEs.setTitle(postBean.getPostTitle());
            postBeanEs.setContext(postBean.getPostContent());
            postBeanEs.setHits(postBean.getPostPageView());
            postBeanEs.setTime(postBean.getPostTime());
            postBeanEs.setCommentNumber(postBean.getPostCommentNumber());
            postBeanEs.setLikeNumber(postBean.getPostLikeNumber());
            postBeanEs.setPostImageAddress(postBean.getPostImageAddress());
            postBeanEs.setForward(postBean.getPostForward());
            postBeanEs.setUid(postBean.getPostMemberId());
            postMapperEs.save(postBeanEs);
        }
    }

    /**
     * 根据作者id查询昨天的总浏览量
     *
     * @param memberId 用户id
     * @return String
     */
    @Override
    public String selectYesterdayView(Integer memberId) {
        String view = (String) redisTemplate.opsForHash().get(String.valueOf(memberId), "view");
        return view;
    }

    /**
     * 根据帖子id实现置顶功能
     *
     * @param postId 帖子id
     * @return boolean
     */
    @Override
    public boolean updatePostTopById(Integer postId) {
        UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("post_id", postId).set("post_top", 1);
        int update = postMapper.update(null, updateWrapper);
        return update == 1;
    }

    /**
     * 根据贴子id取消置顶
     *
     * @param postId 帖子id
     * @return boolean
     */
    @Override
    public boolean updatePostNoTopById(Integer postId) {
        UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("post_id", postId).set("post_top", 0);
        int update = postMapper.update(null, updateWrapper);
        return update == 1;
    }

}

