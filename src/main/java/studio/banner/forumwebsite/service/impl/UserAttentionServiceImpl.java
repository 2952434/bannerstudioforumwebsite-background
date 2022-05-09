package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserAttentionBean;
import studio.banner.forumwebsite.mapper.MemberInformationMapper;
import studio.banner.forumwebsite.mapper.UserContactMapper;
import studio.banner.forumwebsite.service.IMemberInformationService;
import studio.banner.forumwebsite.service.IUserAttentionService;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.util.List;

/**
 * @Author: Mo
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
    @Autowired
    private IMemberInformationService iMemberInformationService;

    /**
     * 新增关注
     *
     * @param userAttentionBean 关注实体
     * @return boolean
     */
    @Override
    public RespBean insertContact(UserAttentionBean userAttentionBean) {
        if (userAttentionBean.getBeAttentionId() != null) {
            userAttentionBean.setContactTime(TimeUtils.getDateString());
            userAttentionBean.setAttentionShow(0);
            if (userContactMapper.insert(userAttentionBean)!=1){
                logger.error("添加关注失败");
                return RespBean.error("添加关注失败");
            }
            logger.info("添加关注成功");
            UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("member_id", userAttentionBean.getBeAttentionId()).setSql("`member_fans`=`member_fans`+1")
                    .eq("member_id", userAttentionBean.getAttentionId()).setSql("`member_attention`=`member_attention`+1");
            if (memberInformationMapper.update(null,updateWrapper)==1) {
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
        updateWrapper.eq("member_id",attentionId).set("member_attention",selectStarsByMemberId(beAttentionId));
        return memberInformationMapper.update(null,updateWrapper)==1;
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
     *
     * @param memberFan 粉丝id
     * @return List<UserAttentionBean>
     */
    @Override
    public List<UserAttentionBean> stars(Integer memberFan,Integer page) {
        Page<UserAttentionBean> page1 = new Page<>(page, 12);
        QueryWrapper<UserAttentionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("attention_id", memberFan).orderByDesc("contact_time");
        Page<UserAttentionBean> userAttentionBeanPage = userContactMapper.selectPage(page1, queryWrapper);
        List<UserAttentionBean> records = userAttentionBeanPage.getRecords();
        for (UserAttentionBean record : records) {
            MemberInformationBean memberInformationBean = iMemberInformationService.selectUserMsg(record.getBeAttentionId());
            record.setAttentionHead(memberInformationBean.getMemberHead());
            record.setAttentionName(memberInformationBean.getMemberName());
            record.setMemberSignature(memberInformationBean.getMemberSignature());
        }
        return records;
    }

    /**
     * 根据用户Id查询其粉丝
     *
     * @param memberStar 用户id
     * @return List<UserAttentionBean>
     */
    @Override
    public List<UserAttentionBean> fans(Integer memberStar,Integer page) {
        Page<UserAttentionBean> page1 = new Page<>(page, 12);
        QueryWrapper<UserAttentionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("be_attention_id", memberStar).orderByDesc("contact_time");
        Page<UserAttentionBean> userAttentionBeanPage = userContactMapper.selectPage(page1, queryWrapper);
        List<UserAttentionBean> records = userAttentionBeanPage.getRecords();
        for (UserAttentionBean record : records) {
            MemberInformationBean memberInformationBean = iMemberInformationService.selectUserMsg(record.getAttentionId());
            record.setAttentionHead(memberInformationBean.getMemberHead());
            record.setAttentionName(memberInformationBean.getMemberName());
            record.setMemberSignature(memberInformationBean.getMemberSignature());
        }
        return records;
    }

    @Override
    public List<UserAttentionBean> selectAttentionInformation(Integer memberId, Integer page) {
        Page<UserAttentionBean> page1 = new Page<>(page, 12);
        QueryWrapper<UserAttentionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("be_attention_id", memberId).eq("attention_show",0).orderByDesc("attention_time");
        Page<UserAttentionBean> userAttentionBeanPage = userContactMapper.selectPage(page1, queryWrapper);
        List<UserAttentionBean> records = userAttentionBeanPage.getRecords();
        for (UserAttentionBean record : records) {
            MemberInformationBean memberInformationBean = iMemberInformationService.selectUserMsg(record.getAttentionId());
            record.setAttentionHead(memberInformationBean.getMemberHead());
            record.setAttentionName(memberInformationBean.getMemberName());
        }
        return records;
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
