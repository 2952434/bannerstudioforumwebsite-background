package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.mapper.UserMapper;
import studio.banner.forumwebsite.service.IUserService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 22:05
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 增加数据
     * @param userBean  插入的用户
     * @return  boolean
     */
    @Override
    public boolean insertUser(UserBean userBean) {
        if (selectAccount(userBean.getMemberAccountNumber())==true){
            userMapper.insert(userBean);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 查询账号是否存在
     * @param memberAccountNumber  查询的用户账号
     * @return  boolean
     */
    @Override
    public boolean selectAccount(Integer memberAccountNumber) {
        QueryWrapper<UserBean> wrapper = new QueryWrapper<>();
        wrapper.eq("member_account_number",memberAccountNumber);
        List User = userMapper.selectList(wrapper);
        if (User.size() == 0){
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * 查询账号密码是否对应存在
     * @param MemberAccountNumber  查询用户账号
     * @param memberPassword  查询用户账号
     * @return  boolean
     */
    @Override
    public boolean selectUser(Integer MemberAccountNumber,String memberPassword) {
        QueryWrapper<UserBean> wrapper = new QueryWrapper<>();
        wrapper.eq("member_account_number",MemberAccountNumber)
                .eq("member_password",memberPassword);
        List User = userMapper.selectList(wrapper);
        if (User.size() ==1){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 根据Id注销用户
     * @param memberId  传入Id
     * @return  boolean
     */
    @Override
    public boolean deleteUser(Integer memberId) {
        int i = userMapper.deleteById(memberId);
        return i == 1;
    }

    @Override
    public boolean updateUser() {
        return false;
    }

    /**
     * 修改密码
     * @param memberId  输入Id
     * @param memberPassword  输入原密码
     * @param newMemberPassword   输入新密码
     * @return  boolean
     */
    @Override
    public boolean updateUserPassWord(Integer memberId, String memberPassword, String newMemberPassword) {
        UserBean user = new UserBean();
        List<UserBean> list = new LambdaQueryChainWrapper<>(userMapper)
                .eq(UserBean::getMemberPassword, memberPassword).list();
        if (list.size()>0){
            user.setMemberId(memberId);
            user.setMemberPassword(newMemberPassword);
            int i = userMapper.updateById(user);
            return i == 1;
        }else{
            return false;
        }
        }

    /**
     * 忘记密码，根据手机号修改密码
     * @param memberAccountNumber
     * @param memberPhone
     * @param newMemberPassword
     * @return boolean
     */
    @Override
    public boolean forgetPassWord(String memberAccountNumber,String memberPhone,String newMemberPassword) {
        UserBean user = new UserBean();
        List<UserBean> list = new LambdaQueryChainWrapper<>(userMapper)
                .eq(UserBean::getMemberAccountNumber,memberAccountNumber)
                .eq(UserBean::getMemberPhone, memberPhone).list();
        if (list.size()>0){
            user.setMemberId(list.get(0).getMemberId());
            user.setMemberPhone(memberPhone);
            user.setMemberPassword(newMemberPassword);
            int i = userMapper.updateById(user);
            return i == 1;
        }else{
            return false;
        }
    }

    /**
     * 根据账号查询用户
     * @param memberAccount  输入账号
     * @return  UserBean
     */
    @Override
    public UserBean selectUser(Integer memberAccount) {
        QueryWrapper<UserBean> wrapper = new QueryWrapper<>();
        wrapper.eq("member_account_number",memberAccount);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public IPage<UserBean> selectUser() {
        return null;
    }
}
