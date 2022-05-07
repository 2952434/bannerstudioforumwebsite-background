package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.manager.SendMail;
import studio.banner.forumwebsite.mapper.MemberInformationMapper;
import studio.banner.forumwebsite.service.*;
import studio.banner.forumwebsite.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @Author: Mo
 * @Date: 2021/5/17 17:21
 * @role: 用户信息类实现
 */
@Service
public class MemberInformationServiceImpl implements IMemberInformationService {
    protected static Logger logger = LoggerFactory.getLogger(MemberInformationServiceImpl.class);

    @Autowired
    private MemberInformationMapper memberInformationMapper;
    @Autowired
    private SendMail sendMail;
    @Autowired
    private ICollectService iCollectService;


    /**
     * 新增用户信息
     *
     * @param memberInformationBean 用户信息实体
     * @return boolean
     */
    @Override
    public RespBean insertUserMsg(MemberInformationBean memberInformationBean) {
        if (selectUserById(memberInformationBean.getMemberId())==null){
            memberInformationBean.setMemberBirthday(TimeUtils.getDateString01());
            if (memberInformationMapper.insert(memberInformationBean) == 1) {
                logger.info("成员插入成功");
                return RespBean.ok("成员插入成功");
            }else {
                logger.error("成员插入失败");
                return RespBean.error("成员插入失败");
            }
        }else {
            return RespBean.error("已存在该用户");
        }
    }


    /**
     * 根据Id查询用户信息
     *
     * @param memberId 用户id
     * @return MemberInformationBean
     */
    @Override
    public MemberInformationBean selectUserMsg(Integer memberId) {
        QueryWrapper<MemberInformationBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        //        memberInformationBean.setMemberFans(iUserAttentionService.selectFansByMemberId(memberId));
//        memberInformationBean.setMemberAttention(iUserAttentionService.selectStarsByMemberId(memberId));
//        memberInformationBean.setMemberPostNum(iPostService.selectPostNumByMemberId(memberId));
//        memberInformationMapper.update(memberInformationBean,queryWrapper);
        return memberInformationMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户id查询用户生日
     *
     * @param id 用户id
     * @return boolean
     */
    @Override
    public boolean selectBirthdayById(Integer id) {
        String time = TimeUtils.getDateString01();
        String[] split1 = time.split("-");
        QueryWrapper<MemberInformationBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", id);
        MemberInformationBean memberInformationBean = memberInformationMapper.selectOne(queryWrapper);
        String birthday = memberInformationBean.getMemberBirthday();
        String[] split = birthday.split("-");
        if (split[1].equals(split1[1]) && split[2].equals(split1[2])) {
            return true;
        }
        return false;
    }


    @Override
    public List<MemberInformationBean> selectBirthday(Integer memberId) {
        String time = TimeUtils.getDateString01();
        String[] split = time.split("-");
        String time01 = split[1] + "-" + split[2];
        QueryWrapper<MemberInformationBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("member_birthday", time01);
        List<MemberInformationBean> list = memberInformationMapper.selectList(queryWrapper);
        list.removeIf(userMsgBean -> userMsgBean.getMemberId().equals(memberId));
        return list;
    }

    /**
     * 通过邮箱祝福过生日的人
     *
     * @param content  祝福内容
     * @param memberId 被祝福人的id
     * @return boolean
     */
    @Override
    public boolean blessUserBirthday(Integer memberId, String content) {
        QueryWrapper<MemberInformationBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        MemberInformationBean memberInformationBean = memberInformationMapper.selectOne(queryWrapper);
        return sendMail.sendBlessMail(memberInformationBean.getMemberEmail(), content);
    }

    /**
     * 系统每天早上0点自动监测过生日的人并发送祝福邮件
     */
    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void automaticSentMail() {
        String time = TimeUtils.getDateString01();
        String[] split = time.split("-");
        String time01 = split[1] + "-" + split[2];
        QueryWrapper<MemberInformationBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("member_birthday", time01);
        List<MemberInformationBean> list = memberInformationMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            for (MemberInformationBean memberInformationBean : list) {
                sendMail.automaticMail(memberInformationBean.getMemberEmail(), memberInformationBean.getMemberName());
            }
        }
    }


    /**
     * 根据id查询用户是否存在
     *
     * @param memberId 用户id
     * @return MemberInformationBean
     */
    @Override
    public MemberInformationBean selectUserById(Integer memberId) {
        QueryWrapper<MemberInformationBean> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        return memberInformationMapper.selectOne(wrapper);
    }

    @Override
    public void updateColNum(Integer memberId) {
        UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id",memberId).set("member_collect_num",iCollectService.selectCollectNumByUserId(memberId));
        memberInformationMapper.update(null,updateWrapper);
    }

    @Override
    public void increaseLikeNum(Integer memberId) {
        UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id",memberId).set("member_like_num",selectUserMsg(memberId).getMemberLikeNum()+1);
        memberInformationMapper.update(null,updateWrapper);
    }

    @Override
    public void underLikeNum(Integer memberId) {
        UpdateWrapper<MemberInformationBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id",memberId).set("member_like_num",selectUserMsg(memberId).getMemberLikeNum()-1);
        memberInformationMapper.update(null,updateWrapper);
    }


    /**
     *  根据用户id查询用户所有信息
     * @param memberId 用户id
     * @return Map<String,String>
     */
    @Override
    public RespBean selectAllInformationByMemberId(Integer memberId) {

        Map<String, String> map = memberInformationMapper.selectAllInformationByMemberId(memberId);
        return RespBean.ok("查询成功", map);
    }


}
