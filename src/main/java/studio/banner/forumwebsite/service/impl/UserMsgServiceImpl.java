package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.bean.UserContactBean;
import studio.banner.forumwebsite.bean.UserMsgBean;
import studio.banner.forumwebsite.mapper.UserContactMapper;
import studio.banner.forumwebsite.mapper.UserMapper;
import studio.banner.forumwebsite.mapper.UserMsgMapper;
import studio.banner.forumwebsite.service.IUserMsgService;
import studio.banner.forumwebsite.service.IUserService;

import javax.management.monitor.StringMonitor;
import java.util.List;

/**
 * @Author: Mo
 * @Date: 2021/5/17 17:21
 */
@Service
public class UserMsgServiceImpl implements IUserMsgService {
    protected static Logger logger = LoggerFactory.getLogger(UserMsgServiceImpl.class);

    @Autowired
    private UserMsgMapper userMsgMapper;

    /**
     * 注册账户时调用，初始化用户信息表
     * @param  memberId;
     * @param  memberName;
     * @param  memberSex;
     * @param  memberAge;
     * @param  memberTime;
     * @param  memberHead;
     * @param  memberFans;
     * @param  memberAttention;
     * @return boolean
     */
    @Override
    public boolean insertUserMsg(Integer memberId, String memberName,String memberSex,Integer memberAge,String memberTime,String memberHead,Integer memberFans,Integer memberAttention) {
        if (memberId != null){
            UserMsgBean userMsgBean =new UserMsgBean(memberId,memberName,memberSex,memberAge,memberTime,memberHead,memberFans,memberAttention);
            userMsgMapper.insert(userMsgBean);
            return true;
        }
            return false;
    }

    /**
     * 根据用户Id更改用户名
     * @param memberId
     * @param memberName
     * @return boolean
     */
    @Override
    public boolean updateUserName(Integer memberId,String memberName) {
        UserMsgBean userMsg = new UserMsgBean();
        List<UserMsgBean> list = new LambdaQueryChainWrapper<>(userMsgMapper)
                .eq(UserMsgBean::getMemberId, memberId).list();
        if (list.size()>0){
            userMsg.setMemberId(memberId);
            userMsg.setMemberName(memberName);
            int i = userMsgMapper.updateById(userMsg);
            return i == 1;
        }else{
            return false;
        }
    }

    @Autowired
    private UserMsgMapper userMsgMapperSex;
    /**
     * 根据用户Id更改性别
     * @param memberId
     * @param memberSex
     * @return  boolean
     */
    @Override
    public boolean updateUserSex(Integer memberId, String memberSex) {
        UserMsgBean userMsgSex = new UserMsgBean();
        List<UserMsgBean> list = new LambdaQueryChainWrapper<>(userMsgMapper)
                .eq(UserMsgBean::getMemberId, memberId).list();
        if (list.size()!=0){
            logger.info(userMsgSex.toString());
            userMsgSex.setMemberId(memberId);
            userMsgSex.setMemberSex(memberSex);
            int i = userMsgMapper.updateById(userMsgSex);
            return i == 1;
        }else{
            return false;
        }
    }

    /**
     * 根据用户Id更改年龄
     * @param memberId
     * @param memberAge
     * @return  boolean
     */
    @Override
    public boolean updateUserAge(Integer memberId, Integer memberAge) {
        UserMsgBean userMsg = new UserMsgBean();
        List<UserMsgBean> list = new LambdaQueryChainWrapper<>(userMsgMapper)
                .eq(UserMsgBean::getMemberId, memberId).list();
        if (list.size()>0){
            userMsg.setMemberId(memberId);
            userMsg.setMemberAge(memberAge);
            int i = userMsgMapper.updateById(userMsg);
            return i == 1;
        }else{
            return false;
        }
    }

    /**
     * 根据用户Id更改头像
     * @param memberId
     * @param memberHead
     * @return boolean
     */
    @Override
    public boolean updateUserHead(Integer memberId, String memberHead) {
        UserMsgBean userMsg = new UserMsgBean();
        List<UserMsgBean> list = new LambdaQueryChainWrapper<>(userMsgMapper)
                .eq(UserMsgBean::getMemberId, memberId).list();
        if (list.size()>0){
            userMsg.setMemberId(memberId);
            userMsg.setMemberHead(memberHead);
            int i = userMsgMapper.updateById(userMsg);
            return i == 1;
        }else{
            return false;
        }
    }
}
