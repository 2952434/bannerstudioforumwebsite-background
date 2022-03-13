package studio.banner.forumwebsite.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.mapper.MemberInformationMapper;
import studio.banner.forumwebsite.mapper.PostMapper;
import studio.banner.forumwebsite.service.IRedisService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Ljx
 * @Date: 2022/3/12 22:11
 * @role:
 */
@Service
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private MemberInformationMapper memberInformationMapper;

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String POST_RANK = "post_rank";
    /**
     * 将数据库中的帖子导入到Redis中
     *
     * @return Set<ZSetOperations.TypedTuple < String>>
     */
    @Override
    public Set<ZSetOperations.TypedTuple<String>> addRedis() {
        redisTemplate.opsForZSet().removeRangeByScore(POST_RANK, 0, 100);
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("post_page_view","post_like_number");
        Page<PostBean> page = new Page<>(1,20);
        Page<PostBean> page1 = postMapper.selectPage(page, queryWrapper);
        List<PostBean> records = page1.getRecords();
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        if (CollectionUtils.isNotEmpty(records)) {
            for (PostBean postBean : records) {
                DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(JSON.toJSONString(postBean), (double) postBean.getPostPageView());
                tuples.add(tuple);
            }
        }
        redisTemplate.opsForZSet().add(POST_RANK, tuples);
        return redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 15);
    }

    /**
     * 每天早上1点自动更新Redis数据库中的帖子排名
     *
     * @return Set<ZSetOperations.TypedTuple < String>>
     */
    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public Set<ZSetOperations.TypedTuple<String>> updateRedisPostRank() {
        redisTemplate.opsForZSet().removeRangeByScore(POST_RANK, 0, 20);
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("post_page_view","post_like_number");
        Page<PostBean> page = new Page<>(1,20);
        Page<PostBean> page1 = postMapper.selectPage(page, queryWrapper);
        List<PostBean> records = page1.getRecords();
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        if (CollectionUtils.isNotEmpty(records)) {
            for (PostBean postBean : records) {
                DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(JSON.toJSONString(postBean), (double) postBean.getPostPageView());
                tuples.add(tuple);
            }
        }
        redisTemplate.opsForZSet().add(POST_RANK, tuples);
        return redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 15);
    }

    /**
     * 帖子排行榜查询
     *
     * @return Set<ZSetOperations.TypedTuple < String>>
     */
    @Override
    public Set<ZSetOperations.TypedTuple<String>> selectPostRank() {
        return redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 15);
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateMemberViewNum() {
        List<MemberInformationBean> memberInformationBeans = memberInformationMapper.selectList(null);
        for (MemberInformationBean memberInformationBean : memberInformationBeans) {
            Integer yesterdayViewNum = (Integer) redisTemplate.opsForHash().get(String.valueOf(memberInformationBean.getMemberId()), "yesterdayViewNum");
            if (yesterdayViewNum==null){
                for (int i = 0; i < 29; i++) {
                    redisTemplate.opsForList().leftPush("viewNum"+memberInformationBean.getMemberId(), String.valueOf(0));
                }
                redisTemplate.opsForList().leftPush("viewNum"+memberInformationBean.getMemberId(), String.valueOf(memberInformationBean.getMemberViewNum()));
                redisTemplate.opsForHash().put(String.valueOf(memberInformationBean.getMemberId()), "yesterdayViewNum",memberInformationBean.getMemberViewNum());
            }else {
                Integer view = memberInformationBean.getMemberLikeNum()-yesterdayViewNum;
                redisTemplate.opsForHash().put(String.valueOf(memberInformationBean.getMemberId()), "yesterdayViewNum",memberInformationBean.getMemberViewNum());
                redisTemplate.opsForList().rightPopAndLeftPush("viewNum"+memberInformationBean.getMemberId(), String.valueOf(view));
            }
        }
    }

    @Override
    public List<Integer> selectEveryDayAddViewNum(Integer memberId) {
        List<String> viewNum = redisTemplate.opsForList().range("viewNum", 0L, 14L);
        if (viewNum==null){
            return null;
        }
        List<Integer> viewNumList = new ArrayList<>();
        CollectionUtils.collect(viewNum, o -> Integer.valueOf(o.toString()), viewNumList);
        return viewNumList;
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateForumViewNum() {
        Integer yesterdayViewNum = (Integer) redisTemplate.opsForHash().get("forumPostViewNum", "yesterdayViewNum");
        Integer todayViewNum = (Integer) redisTemplate.opsForHash().get("forumPostViewNum", "todayViewNum");
        if (todayViewNum==null){
            todayViewNum = 0;
        }
        if (yesterdayViewNum==null){
            for (int i = 0; i < 29; i++) {
                redisTemplate.opsForList().leftPush("forumPostViewNumList", String.valueOf(0));
            }
            redisTemplate.opsForList().leftPush("forumPostViewNumList", String.valueOf(todayViewNum));
            redisTemplate.opsForHash().put("forumPostViewNum", "yesterdayViewNum",todayViewNum);
        }else {
            Integer forumViewNum = todayViewNum - yesterdayViewNum;
            redisTemplate.opsForHash().put("forumPostViewNum", "yesterdayViewNum",todayViewNum);
            redisTemplate.opsForList().rightPopAndLeftPush("forumPostViewNumList", String.valueOf(forumViewNum));
        }
    }

    @Override
    public List<Integer> selectForumEveryDayAddViewNum() {
        List<String> forumPostViewNum = redisTemplate.opsForList().range("forumPostViewNumList", 0L, 14L);
        if (forumPostViewNum==null){
            updateForumViewNum();
            List<String> forumPostViewNum1 = redisTemplate.opsForList().range("forumPostViewNumList", 0L, 14L);
            List<Integer> viewNumList = new ArrayList<>();
            CollectionUtils.collect(forumPostViewNum1, o -> Integer.valueOf(o.toString()), viewNumList);
            return viewNumList;
        }
        List<Integer> viewNumList = new ArrayList<>();
        CollectionUtils.collect(forumPostViewNum, o -> Integer.valueOf(o.toString()), viewNumList);
        return viewNumList;
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateForumPostInsertNum() {
        Integer yesterdayPostInsertNum = (Integer) redisTemplate.opsForHash().get("forumPostInsertNum", "yesterdayPostInsertNum");
        Integer todayPostInsertNum = (Integer) redisTemplate.opsForHash().get("forumPostPostInsertNum", "todayPostInsertNum");
        if (todayPostInsertNum==null){
            todayPostInsertNum = 0;
        }
        if (yesterdayPostInsertNum==null){
            for (int i = 0; i < 29; i++) {
                redisTemplate.opsForList().leftPush("forumPostInsertNum", String.valueOf(0));
            }
            redisTemplate.opsForList().leftPush("forumPostInsertNum", String.valueOf(todayPostInsertNum));
            redisTemplate.opsForHash().put("forumPostInsertNum", "yesterdayPostInsertNum",todayPostInsertNum);
        }else {
            Integer forumPostInsertNum = todayPostInsertNum - yesterdayPostInsertNum;
            redisTemplate.opsForHash().put("forumPostInsertNum", "yesterdayPostInsertNum",todayPostInsertNum);
            redisTemplate.opsForList().rightPopAndLeftPush("forumPostInsertNum", String.valueOf(forumPostInsertNum));
        }
    }

    @Override
    public List<Integer> selectForumPostInsertNum() {
        List<String> forumPostInsertNum = redisTemplate.opsForList().range("forumPostInsertNum", 0L, 14L);
        if (forumPostInsertNum==null){
            updateForumPostInsertNum();
            List<String> forumPostInsertNum1 = redisTemplate.opsForList().range("forumPostInsertNum", 0L, 14L);
            List<Integer> postInsertNumList = new ArrayList<>();
            CollectionUtils.collect(forumPostInsertNum1, o -> Integer.valueOf(o.toString()), postInsertNumList);
            return postInsertNumList;
        }
        List<Integer> postInsertNumList = new ArrayList<>();
        CollectionUtils.collect(forumPostInsertNum, o -> Integer.valueOf(o.toString()), postInsertNumList);
        return postInsertNumList;
    }


}
