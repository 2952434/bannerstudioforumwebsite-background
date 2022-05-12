package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserAttentionBean;
import studio.banner.forumwebsite.mapper.MemberInformationMapper;
import studio.banner.forumwebsite.mapper.UserContactMapper;
import studio.banner.forumwebsite.service.IMemberInformationService;
import studio.banner.forumwebsite.service.IUserAttentionService;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2021/5/18 18:35
 * @role: 关注关系服务实现
 */
@Service
public class UserAttentionServiceImpl implements IUserAttentionService {
    protected static Logger logger = LoggerFactory.getLogger(MemberInformationServiceImpl.class);

    @Autowired
    private UserContactMapper userContactMapper;
    @Autowired
    private MemberInformationMapper memberInformationMapper;

    /**
     * 新增关注
     *
     * @param userAttentionBean 关注实体
     * @return boolean
     */
    @Override
    @Transactional
    public RespBean insertContact(UserAttentionBean userAttentionBean) {
        if (contacted(userAttentionBean.getAttentionId(),userAttentionBean.getBeAttentionId())){
            return RespBean.error("以关注过该用户");
        }
        if (userAttentionBean.getBeAttentionId() != null) {
            userAttentionBean.setContactTime(TimeUtils.getDateString());
            userAttentionBean.setAttentionShow(0);
            if (userContactMapper.insert(userAttentionBean)!=1){
                logger.error("添加关注失败");
                return RespBean.error("添加关注失败");
            }
            logger.info("添加关注成功");
            UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("member_id",userAttentionBean.getBeAttentionId()).set("member_fans",selectFansByMemberId(userAttentionBean.getBeAttentionId()));
            UpdateWrapper<MemberInformationBean> updateWrapper1 = new UpdateWrapper<>();
            updateWrapper1.eq("member_id",userAttentionBean.getAttentionId()).set("member_attention",selectStarsByMemberId(userAttentionBean.getAttentionId()));
            if (memberInformationMapper.update(null,updateWrapper)==1&&memberInformationMapper.update(null,updateWrapper1)==1) {
                return RespBean.ok("添加关注成功");
            }
        }
        return RespBean.error("添加关注失败");
    }

    /**
     * 取消关注
     *
     * @param attentionId 关注id
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteContact(Integer attentionId,Integer beAttentionId) {
        UpdateWrapper<UserAttentionBean> wrapper = new UpdateWrapper<>();
        wrapper.eq("attention_id", attentionId).eq("be_attention_id",beAttentionId);
        if (userContactMapper.delete(wrapper)!=1){
            logger.error("取消关注失败");
            return false;
        }
        logger.info("取消关注成功");
        UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id",beAttentionId).set("member_fans",selectFansByMemberId(beAttentionId));
        UpdateWrapper<MemberInformationBean> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("member_id",attentionId).set("member_attention",selectStarsByMemberId(attentionId));
        return memberInformationMapper.update(null,updateWrapper)==1&&memberInformationMapper.update(null,updateWrapper1)==1;
    }

    /**
     * 查询是否存在关注关系
     *
     * @param memberFan  粉丝id
     * @param memberStar 被关注者id
     * @return
     */
    @Override
    public boolean contacted(Integer memberFan, Integer memberStar) {
        QueryWrapper<UserAttentionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attention_id", memberFan)
                .eq("be_attention_id", memberStar);
        List<UserAttentionBean> list = userContactMapper.selectList(queryWrapper);
        return list.size()==1;
    }

    @Override
    public Integer selectFansByMemberId(Integer memberId) {
        QueryWrapper<UserAttentionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("be_attention_id", memberId);
        return userContactMapper.selectCount(queryWrapper);
    }

    /**
     * 根据Id查询其关注的人
     * @param userId
     * @param page
     * @return
     */
    @Override
    public List<Map<String, String>> selectAttentionUserId(Integer userId, Integer page) {
        return userContactMapper.selectAttentionUserId(userId,page*12);
    }

    /**
     * 根据用户Id查询其粉丝
     * @param userId
     * @param page
     * @return
     */
    @Override
    public List<Map<String, String>> selectFanByUserId(Integer userId, Integer page) {
        return userContactMapper.selectFanByUserId(userId, page * 12);
    }

    @Override
    public List<Map<String, String>> selectAttentionInformation(Integer memberId, Integer page) {
        return userContactMapper.selectFanByUserId(memberId, page * 12);
    }

    @Override
    public Integer selectStarsByMemberId(Integer memberId) {
        QueryWrapper<UserAttentionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attention_id", memberId);
        return userContactMapper.selectCount(queryWrapper);
    }

    @Override
    public RespBean deleteAttentionInformation(Integer id) {
        UpdateWrapper<UserAttentionBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("attention_show",1);
        if (userContactMapper.update(null,updateWrapper)==1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @Override
    public RespBean deleteAllAttentionInformation(Integer userId) {
        UpdateWrapper<UserAttentionBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("be_attention_id",userId).set("attention_show",1);
        if (userContactMapper.update(null,updateWrapper)==1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


}
