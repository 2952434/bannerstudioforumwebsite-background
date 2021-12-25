package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.User;
import studio.banner.forumwebsite.bean.UserBean;
//import studio.banner.forumwebsite.bean.UserInfo;
import studio.banner.forumwebsite.manager.SendMail;
import studio.banner.forumwebsite.mapper.UserMapper;
import studio.banner.forumwebsite.service.IUserService;
import studio.banner.forumwebsite.utils.JwtUtils;

import javax.servlet.http.HttpSession;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 22:05
 * @role: 用户服务类实现
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SendMail sendMail;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 插入用户
     *
     * @param userBean 用户实体
     * @return boolean
     */
    @Override
    public boolean insertUser(UserBean userBean) {
        if (selectAccount(userBean.getMemberPhone())) {
            userBean.setMemberAdmin("user");
            userMapper.insert(userBean);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询账号是否存在
     *
     * @param memberPhone 查询的用户账号
     * @return boolean
     */
    @Override
    public boolean selectAccount(String memberPhone) {
        QueryWrapper<UserBean> wrapper = new QueryWrapper<>();
        wrapper.eq("member_phone", memberPhone);
        List<UserBean> user = userMapper.selectList(wrapper);
        return user.size() == 0;
    }


    /**
     * 登陆时查询是否存在该账号与对应密码
     *
     * @param memberPhone 用户账号
     * @param memberPassword 用户密码
     * @return List<UserBean>
     * @throws Exception io异常
     */
    @Override
    public List<UserBean> selectUser(String memberPhone, String memberPassword) throws Exception {
        QueryWrapper<UserBean> wrapper = new QueryWrapper<>();
        wrapper.eq("member_phone", memberPhone)
                .eq("member_password", memberPassword);
        List<UserBean> user = userMapper.selectList(wrapper);
        if (user.size()!=0){
            PrivateKey privateKey = (PrivateKey) redisTemplate.opsForHash().get("keys", "privateKey");
            System.out.println("私钥为："+privateKey);
//            String token = JwtUtils.generateToken(new UserInfo(MemberPhone, memberPassword), privateKey, 8);
//            redisTemplate.opsForValue().set(MemberPhone,token,10, TimeUnit.MINUTES);
        }
        return user;
    }

    /**
     * 根据Id注销用户
     *
     * @param memberId 传入Id
     * @return boolean
     */
    @Override
    public boolean deleteUser(Integer memberId) {
        int i = userMapper.deleteById(memberId);
        return i == 1;
    }
    /**
     * 更新用户
     *
     * @return boolean
     */
    @Override
    public boolean updateUser() {
        return false;
    }

    /**
     * 修改密码
     *
     * @param memberId          输入Id
     * @param memberPassword    输入原密码
     * @param newMemberPassword 输入新密码
     * @param repeatPassword    重复新密码
     * @return boolean
     */
    @Override
    public boolean updateUserPassWord(Integer memberId, String memberPassword, String newMemberPassword, String repeatPassword) {
        if (newMemberPassword.equals(repeatPassword)) {
            UserBean user = new UserBean();
            List<UserBean> list = new LambdaQueryChainWrapper<>(userMapper)
                    .eq(UserBean::getMemberPassword, memberPassword).list();
            if (list.size() > 0) {
                System.out.println("找到的list为" + list);
                user.setMemberId(memberId);
                user.setMemberPassword(newMemberPassword);
                int i = userMapper.updateById(user);
                return i == 1;
            } else {
                System.out.println("未找到用户" + list);
                return false;
            }
        } else {
            System.out.println("两次输入的密码不同" + newMemberPassword + repeatPassword);
            return false;
        }
    }

    /**
     * 忘记密码，根据邮箱修改密码
     *
     * @param memberPhone 账号
     * @param memberMail 邮箱
     * @param code 验证码
     * @param newMemberPassword 新密码
     * @return boolean
     */
    @Override
    public boolean forgetPassWord(String memberPhone, String memberMail, String code, String newMemberPassword) {
        UserBean user = new UserBean();
        List<UserBean> list = new LambdaQueryChainWrapper<>(userMapper)
                .eq(UserBean::getMemberPhone, memberPhone)
                .eq(UserBean::getMemberMail, memberMail).list();
        if (list.size() > 0) {
            user.setMemberId(list.get(0).getMemberId());
            user.setMemberMail(memberMail);
            user.setMemberPassword(newMemberPassword);
            int i = userMapper.updateById(user);
            return i == 1;
        } else {
            return false;
        }
    }

    /**
     * 根据账号查询用户
     *
     * @param memberPhone 输入账号
     * @return UserBean
     */
    @Override
    public UserBean selectUser(String memberPhone) {
        QueryWrapper<UserBean> wrapper = new QueryWrapper<>();
        wrapper.eq("member_phone", memberPhone);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 分页查询用户
     *
     * @return UserBean
     */
    @Override
    public IPage<UserBean> selectUser() {
        return null;
    }

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @param phone 账号
     * @return List<UserBean>
     */
    @Override
    public List<UserBean> sendMail(String email, String phone) {
        String code = sendMail.sendSimpleMail(email);
        QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_phone", phone).eq("member_mail", email);
        List<UserBean> list = userMapper.selectList(queryWrapper);
        if (list.size() == 1) {
            list.get(0).setMemberCode(code);
            userMapper.update(list.get(0), queryWrapper);
        }
        return list;

    }


}
