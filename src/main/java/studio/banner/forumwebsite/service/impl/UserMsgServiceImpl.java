package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import lombok.Data;
import org.apache.catalina.User;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.bean.UserContactBean;
import studio.banner.forumwebsite.bean.UserMsgBean;
import studio.banner.forumwebsite.manager.SendMail;
import studio.banner.forumwebsite.mapper.UserContactMapper;
import studio.banner.forumwebsite.mapper.UserMapper;
import studio.banner.forumwebsite.mapper.UserMsgMapper;
import studio.banner.forumwebsite.service.IUserMsgService;
import studio.banner.forumwebsite.service.IUserService;

import javax.management.monitor.StringMonitor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @Author: Mo
 * @Date: 2021/5/17 17:21
 * @role: 用户信息类实现
 */
@Service
public class UserMsgServiceImpl implements IUserMsgService {
    protected static Logger logger = LoggerFactory.getLogger(UserMsgServiceImpl.class);

    @Autowired
    private UserMsgMapper userMsgMapper;
    @Autowired
    private UserContactMapper userContactMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SendMail sendMail;

    /**
     * 新增用户信息
     *
     * @param userMsgBean 用户信息实体
     * @return boolean
     */
    @Override
    public boolean insertUserMsg(UserMsgBean userMsgBean) {
        int i = userMsgMapper.insert(userMsgBean);
        if (i == 1) {
            return true;
        }
        return false;
    }

    /**
     * 根据Id更改用户昵称
     *
     * @param memberId      用户id
     * @param newMemberName 用户新姓名
     * @return boolean
     */
    @Override
    public boolean updateUserName(Integer memberId, String newMemberName) {
        if (selectUserById(memberId) != null) {
            UpdateWrapper<UserMsgBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("member_id", memberId).set("member_name", newMemberName);
            int i = userMsgMapper.update(null, updateWrapper);
            return i == 1;
        } else {
            return false;
        }
    }

    /**
     * 根据Id更改用户性别
     *
     * @param memberId  用户id
     * @param memberSex 用户性别
     * @return boolean
     */
    @Override
    public boolean updateUserSex(Integer memberId, String memberSex) {
        if (selectUserById(memberId) != null) {
            UpdateWrapper<UserMsgBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("member_id", memberId).set("member_sex", memberSex);
            int i = userMsgMapper.update(null, updateWrapper);
            return i == 1;
        } else {
            return false;
        }
    }

    /**
     * 根据Id更改用户年龄
     *
     * @param memberId  用户id
     * @param memberAge 用户年龄
     * @return boolean
     */
    @Override
    public boolean updateUserAge(Integer memberId, Integer memberAge) {
        if (selectUserById(memberId) != null) {
            UpdateWrapper<UserMsgBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("member_id", memberId).set("member_age", memberAge);
            int i = userMsgMapper.update(null, updateWrapper);
            return i == 1;
        } else {
            return false;
        }
    }

    /**
     * 根据Id更改用户头像
     *
     * @param memberId   用户id
     * @param memberHead 头像地址
     * @return boolean
     */
    @Override
    public boolean updateUserHead(Integer memberId, String memberHead) {
        if (selectUserById(memberId) != null) {
            UpdateWrapper<UserMsgBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("member_id", memberId).set("member_head", memberHead);
            int i = userMsgMapper.update(null, updateWrapper);
            return i == 1;
        } else {
            return false;
        }
    }

    /**
     * 根据Id查询用户信息
     *
     * @param memberId 用户id
     * @return UserMsgBean
     */
    @Override
    public UserMsgBean selectUserMsg(Integer memberId) {
        QueryWrapper<UserContactBean> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("member_star", memberId);
        Integer stared = userContactMapper.selectCount(wrapper1);
        QueryWrapper<UserContactBean> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("member_fan", memberId);
        Integer faned = userContactMapper.selectCount(wrapper2);
        UpdateWrapper<UserMsgBean> wrapper3 = new UpdateWrapper<>();
        wrapper3.eq("member_id", memberId).set("member_fans", stared).set("member_attention", faned);
        userMsgMapper.update(null, wrapper3);
        QueryWrapper<UserMsgBean> wrapper4 = new QueryWrapper<>();
        wrapper4.eq("member_id", memberId);
        return userMsgMapper.selectOne(wrapper4);
    }

    /**
     * 根据用户id查询用户生日
     *
     * @param id 用户id
     * @return boolean
     */
    @Override
    public boolean selectBirthdayById(Integer id) {
//        获得当前时间
        SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd");
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date = new Date();
        String time = bjSdf.format(date);
        String[] split1 = time.split("-");
//        获取数据库生日
        QueryWrapper<UserMsgBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", id);
        UserMsgBean userMsgBean = userMsgMapper.selectOne(queryWrapper);
        String birthday = bjSdf.format(userMsgBean.getMemberBirthday());
        String[] split = birthday.split("-");
        if (split[1].equals(split1[1]) && split[2].equals(split1[2])) {
            return true;
        }
        return false;
    }

    /**
     * 查询过生日的人
     *
     * @param memberId 用户id
     * @return List
     */
    @Override
    public List<UserMsgBean> selectBirthday(Integer memberId) {
//        获得当前时间
        SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd");
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date = new Date();
        String time = bjSdf.format(date);
        String[] split = time.split("-");
        String time01 = split[1] + "-" + split[2];
        QueryWrapper<UserMsgBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("member_birthday", time01);
        List<UserMsgBean> list = userMsgMapper.selectList(queryWrapper);
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
        UserBean userBean = userMapper.selectById(memberId);
        String memberMail = userBean.getMemberMail();
        boolean b = sendMail.sendBlessMail(memberMail, content);
        return b;
    }

    /**
     * 系统每天早上0点自动监测过生日的人并发送祝福邮件
     */
    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    public void automaticSentMail() {
        //        获得当前时间
        SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd");
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date = new Date();
        String time = bjSdf.format(date);
        String[] split = time.split("-");
        String time01 = split[1] + "-" + split[2];
        QueryWrapper<UserMsgBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("member_birthday", time01);
        List<UserMsgBean> list = userMsgMapper.selectList(queryWrapper);
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                UserBean userBean = userMapper.selectById(list.get(i).getMemberId());
                sendMail.automaticMail(userBean.getMemberMail(), list.get(i).getMemberName());
            }
        }
    }

    /**
     * 根据用户id更改个性签名
     *
     * @param memberId  用户id
     * @param signature 个性签名
     * @return boolean
     */
    @Override
    public boolean updateUserSignature(Integer memberId, String signature) {
        if (selectUserById(memberId) != null) {
            UpdateWrapper<UserMsgBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("member_id", memberId).set("member_signature", signature);
            int update = userMsgMapper.update(null, updateWrapper);
            return update == 1;
        }
        return false;
    }

    /**
     * 根据id查询用户是否存在
     *
     * @param memberId 用户id
     * @return UserMsgBean
     */
    @Override
    public UserMsgBean selectUserById(Integer memberId) {
        QueryWrapper<UserMsgBean> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        UserMsgBean userMsgBean = userMsgMapper.selectOne(wrapper);
        System.out.println(userMsgBean);
        return userMsgBean;
    }
}
