package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.mapper.*;
import studio.banner.forumwebsite.service.ICollectService;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IPostLikeService;
import studio.banner.forumwebsite.service.IPostService;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/08/23:18
 * @Description: 帖子操作的实现类
 */
@Service
public class PostServiceImpl implements IPostService {

    protected static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    protected PostMapper postMapper;

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private ICollectService iCollectService;

    @Autowired
    private PostMapperEs postMapperEs;

    @Autowired
    private IPostLikeService iPostLikeService;
    @Autowired
    private MemberInformationMapper memberInformationMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String POST_RANK = "post_rank";

    /**
     * 增加帖子
     * @param postBean 帖子实体
     * @return
     */
    @Override
    public RespBean insertPost(PostBean postBean) {
        if (postBean == null) {
            return RespBean.error("帖子信息错误添加失败");
        }
        postBean.setPostTime(TimeUtils.getDateString());
        if (postMapper.insert(postBean)==1){
            UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("member_id",postBean.getPostMemberId()).setSql("`member_post_num`=`member_post_num`+1");
            memberInformationMapper.update(null,updateWrapper);
            logger.info("插入帖子成功");
            return RespBean.ok("插入帖子成功");
        }
        return RespBean.error("帖子插入失败");
    }

    /**
     * 根据帖子id删除帖子
     *
     * @param postId 帖子id
     * @return boolean
     */
    @Override
    public RespBean deletePostById(Integer postId) {
        Integer postMemberId = selectPost(postId).getPostMemberId();
        if (postMapper.deleteById(postId)!=1) {
            logger.error("帖子删除失败");
            return RespBean.error("帖子删除失败");
        }
        iCommentService.deleteAllCommentByPostId(postId);
        logger.info("帖子删除成功");
        UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id",postMemberId).setSql("`member_post_num`=`member_post_num`-1");
        memberInformationMapper.update(null,updateWrapper);
        return RespBean.ok("帖子删除成功");
    }

    /**
     * 根据用户id删除用户全部帖子
     *
     * @param postMemberId 用户id
     * @return boolean
     */
    @Override
    public RespBean deleteAllPost(Integer postMemberId) {
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_member_id",postMemberId);
        if (postMapper.delete(queryWrapper)!=1) {
            logger.error("删除该用户帖子失败");
            return RespBean.error("删除该用户帖子失败");
        }
        UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id",postMemberId).setSql("`member_post_num`= 0");
        memberInformationMapper.update(null,updateWrapper);
        logger.info("删除该用户帖子成功");
        iCommentService.deleteAllCommentByMemberId(postMemberId);
        return RespBean.ok("删除该用户帖子成功");
    }

    /**
     * 修改帖子内容
     * @param postBean
     * @return
     */
    @Override
    public RespBean updatePostById(PostBean postBean) {
        if (postBean==null){
            return RespBean.error("帖子修改失败");
        }
        if (postMapper.updateById(postBean)==1) {
            return RespBean.ok("帖子修改成功");
        }
        return RespBean.error("帖子修改失败");
    }


    /**
     * 根据帖子id更改浏览量
     *
     * @param postId 帖子id
     * @return boolean
     */
    @Override
    public boolean updatePostPageView(Integer postId) {
        if (selectPost(postId) != null) {
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id", postId).setSql("`post_page_view`=`post_page_view`+1");
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
    public boolean updatePostCommentNumber(Integer postId) {
        if (selectPost(postId) != null) {
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id", postId).setSql("`post_comment_number`=`post_comment_number`+1");
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
    public boolean updatePostLikeNumber(Integer postId) {
        PostBean postBean = selectPost(postId);
        if (postBean!= null) {
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id", postId).set("post_like_number",iPostLikeService.selectPostLikeNum(postId));
            postMapper.update(null, updateWrapper);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePostColNumByPostId(Integer postId) {
        return false;
    }


    /**
     * 根据帖子id查询帖子
     *
     * @param postId 帖子id
     * @return PostBean
     */
    @Override
    public PostBean selectPost(Integer postId) {
        PostBean postBean = postMapper.selectById(postId);
        postBean.setPostPageView(postBean.getPostPageView()+1);
        postBean.setPostColNum(iCollectService.selectCollectNumByPostId(postId));
        postBean.setPostCommentNumber(iCommentService.selectCommentNum(postId));
        postMapper.updateById(postBean);
        return postBean;
    }

    @Override
    public Integer selectPostNumByMemberId(Integer memberId) {
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_member_id",memberId);
        return postMapper.selectCount(queryWrapper);
    }

    /**
     * 根据用户id查询某用户全部帖子(根据时间返向排序)
     *
     * @param postMemberId 用户id
     * @return List
     */
    @Override
    public List<PostBean> selectAllPostByDescById(Integer postMemberId) {
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
    public List<PostBean> selectAllPostByAscById(Integer postMemberId) {
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

    @Override
    public Integer selectAllPostNum() {
        return postMapper.selectCount(null);
    }

    /**
     * 分页查询所有帖子
     *
     * @param page 页数
     * @return IPage
     */
    @Override
    public IPage<PostBean> selectAllPost(Integer page) {
        Page<PostBean> page1 = new Page<>(page, 20);
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
    public List<PostBeanEs> selectDimPost(Integer page, String dim) {
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

