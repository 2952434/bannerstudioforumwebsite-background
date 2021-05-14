package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    @Override
    public boolean deleteUser() {
        return false;
    }

    @Override
    public boolean updateUser() {
        return false;
    }

    @Override
    public IPage<UserBean> selectUser() {
        return null;
    }
}
