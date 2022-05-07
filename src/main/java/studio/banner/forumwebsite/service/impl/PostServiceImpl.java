package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.mapper.*;
import studio.banner.forumwebsite.service.*;
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
    private IPostEsService iPostEsService;

    @Autowired
    private IPostLikeService iPostLikeService;
    @Autowired
    private MemberInformationMapper memberInformationMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired IMemberInformationService iMemberInformationService;



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
            updateWrapper.eq("member_id",postBean.getPostMemberId()).set("member_post_num",selectPostNumByMemberId(postBean.getPostMemberId()));
            memberInformationMapper.update(null,updateWrapper);
            Integer todayPostInsertNum = (Integer) redisTemplate.opsForHash().get("forumPostInsertNum", "todayPostInsertNum");
            if (todayPostInsertNum==null){
                todayPostInsertNum=0;
            }
            redisTemplate.opsForHash().put("forumPostInsertNum", "todayPostInsertNum",String.valueOf(todayPostInsertNum+1));
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
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        PostBean postBean = postMapper.selectOne(queryWrapper);
        if (postBean==null){
            return RespBean.error("无该帖子");
        }
        Integer postMemberId = postBean.getPostMemberId();
        if (postMapper.deleteById(postId)!=1) {
            logger.error("帖子删除失败");
            return RespBean.error("帖子删除失败");
        }
        iCommentService.deleteAllCommentByPostId(postId);
        logger.info("帖子删除成功");
        UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id",postMemberId).set("member_post_num",selectPostNumByMemberId(postMemberId));
        memberInformationMapper.update(null,updateWrapper);
        iPostEsService.deleteEsPostById(postId);
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
        iPostEsService.deleteEsPostByMemberId(postMemberId);
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
     * 根据帖子id更改点赞量
     *
     * @param postId 帖子id
     */
    @Override
    public void updatePostLikeNumber(Integer postId) {
        QueryWrapper<PostBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        PostBean postBean = postMapper.selectOne(queryWrapper);
        if (postBean!= null) {
            UpdateWrapper<PostBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_id", postId).set("post_like_number",iPostLikeService.selectPostLikeNum(postId));
            postMapper.update(null, updateWrapper);
        }
    }


    /**
     * 根据帖子id查询帖子
     *
     * @param postId 帖子id
     * @return PostBean
     */
    @Override
    public PostBean selectPost(Integer postId) {
        List<Map<String, String>> maps = postMapper.selectPostById(postId);
        if (maps.size()!=1){
            return null;
        }
        PostBean postBean = new PostBean(maps.get(0));
        postBean.setPostPageView(postBean.getPostPageView()+1);
        postBean.setPostColNum(iCollectService.selectCollectNumByPostId(postId));
        postMapper.updateById(postBean);
        UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id",postBean.getPostMemberId()).set("member_view_num",iMemberInformationService.selectUserMsg(postBean.getPostMemberId()).getMemberViewNum()+1);
        memberInformationMapper.update(null,updateWrapper);
        String todayViewNum = (String) redisTemplate.opsForHash().get("forumPostViewNum", "todayViewNum");
        int integer = 0;
        if (todayViewNum!=null){
              integer = Integer.parseInt(todayViewNum);
        }
        redisTemplate.opsForHash().put("forumPostViewNum", "todayViewNum",String.valueOf(integer+1));
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

