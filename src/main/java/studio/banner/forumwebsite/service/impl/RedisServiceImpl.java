package studio.banner.forumwebsite.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.CollectionUtils;
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
        Page<PostBean> page = new Page<>(1,10);
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
        return redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 10);
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
        return redisTemplate.opsForZSet().reverseRangeWithScores(POST_RANK, 0, 10);
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateMemberViewNum() {
        List<MemberInformationBean> memberInformationBeans = memberInformationMapper.selectList(null);
        for (MemberInformationBean memberInformationBean : memberInformationBeans) {
            String yesterdayViewNum1 = (String) redisTemplate.opsForHash().get(String.valueOf(memberInformationBean.getMemberId()), "yesterdayViewNum");
            Integer yesterdayViewNum;
            if (yesterdayViewNum1==null){
                for (int i = 0; i < 14; i++) {
                    redisTemplate.opsForList().leftPush("viewNum"+memberInformationBean.getMemberId(), String.valueOf(0));
                }
                redisTemplate.opsForList().rightPush("viewNum"+memberInformationBean.getMemberId(), String.valueOf(memberInformationBean.getMemberViewNum()));
                redisTemplate.opsForHash().put(String.valueOf(memberInformationBean.getMemberId()), "yesterdayViewNum",String.valueOf(memberInformationBean.getMemberViewNum()));
            }else {
                yesterdayViewNum = Integer.valueOf(yesterdayViewNum1);
                Integer view = memberInformationBean.getMemberLikeNum()-yesterdayViewNum;
                redisTemplate.opsForHash().put(String.valueOf(memberInformationBean.getMemberId()), "yesterdayViewNum",String.valueOf(memberInformationBean.getMemberViewNum()));
                redisTemplate.opsForList().leftPop("viewNum"+memberInformationBean.getMemberId());
                redisTemplate.opsForList().rightPush("viewNum"+memberInformationBean.getMemberId(), String.valueOf(view));
            }
        }
    }

    @Override
    public List<Integer> selectEveryDayAddViewNum(Integer memberId) {
        List<String> viewNum = redisTemplate.opsForList().range("viewNum"+memberId, 0L, 14L);
        assert viewNum != null;
        if (viewNum.size()==0){
            updateMemberViewNum();
            viewNum = redisTemplate.opsForList().range("viewNum"+memberId, 0L, 14L);
        }
        List<Integer> viewNumList = new ArrayList<>();
        CollectionUtils.collect(viewNum, o -> Integer.valueOf(o.toString()), viewNumList);
        return viewNumList;
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateForumViewNum() {
        Integer yesterdayViewNum = (Integer) redisTemplate.opsForHash().get("forumPostViewNum", "yesterdayViewNum");
        String todayViewNumString = (String) redisTemplate.opsForHash().get("forumPostViewNum", "todayViewNum");
        Integer todayViewNum = 0;
        if (todayViewNumString!=null){
            todayViewNum = Integer.valueOf(todayViewNumString);
        }
        if (yesterdayViewNum==null){
            for (int i = 0; i < 14; i++) {
                redisTemplate.opsForList().leftPush("forumPostViewNumList", String.valueOf(0));
            }
            redisTemplate.opsForList().rightPush("forumPostViewNumList", String.valueOf(todayViewNum));
            redisTemplate.opsForHash().put("forumPostViewNum", "yesterdayViewNum",String.valueOf(todayViewNum));
        }else {
            Integer forumViewNum = todayViewNum - yesterdayViewNum;
            redisTemplate.opsForHash().put("forumPostViewNum", "yesterdayViewNum",String.valueOf(todayViewNum));
            redisTemplate.opsForList().leftPop("forumPostViewNumList");
            redisTemplate.opsForList().rightPush("forumPostViewNumList", String.valueOf(forumViewNum));
        }
    }

    @Override
    public List<Integer> selectForumEveryDayAddViewNum() {
        List<String> forumPostViewNum = redisTemplate.opsForList().range("forumPostViewNumList", 0L, 14L);
        assert forumPostViewNum != null;
        if (forumPostViewNum.size()==0){
            updateForumViewNum();
            forumPostViewNum = redisTemplate.opsForList().range("forumPostViewNumList", 0L, 14L);
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
            for (int i = 0; i < 14; i++) {
                redisTemplate.opsForList().leftPush("forumPostInsertNumList", String.valueOf(0));
            }
            redisTemplate.opsForList().rightPush("forumPostInsertNumList", String.valueOf(todayPostInsertNum));
            redisTemplate.opsForHash().put("forumPostInsertNum", "yesterdayPostInsertNum",String.valueOf(todayPostInsertNum));
        }else {
            Integer forumPostInsertNum = todayPostInsertNum - yesterdayPostInsertNum;
            redisTemplate.opsForHash().put("forumPostInsertNum", "yesterdayPostInsertNum",String.valueOf(todayPostInsertNum));
            redisTemplate.opsForList().leftPop("forumPostInsertNumList");
            redisTemplate.opsForList().rightPush("forumPostInsertNumList", String.valueOf(forumPostInsertNum));
        }
    }

    @Override
    public List<Integer> selectForumPostInsertNum() {
        List<String> forumPostInsertNum = redisTemplate.opsForList().range("forumPostInsertNumList", 0L, 14L);
        System.out.println(forumPostInsertNum);
        assert forumPostInsertNum != null;
        if (forumPostInsertNum.size()==0){
            updateForumPostInsertNum();
            forumPostInsertNum = redisTemplate.opsForList().range("forumPostInsertNumList", 0L, 14L);
        }
        List<Integer> postInsertNumList = new ArrayList<>();
        CollectionUtils.collect(forumPostInsertNum, o -> Integer.valueOf(o.toString()), postInsertNumList);
        return postInsertNumList;
    }


}
