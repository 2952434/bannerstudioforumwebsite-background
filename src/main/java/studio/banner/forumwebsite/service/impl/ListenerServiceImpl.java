package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.mapper.ListenerMapper;
import studio.banner.forumwebsite.service.IListenerService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/14 21:09
 * @role: 监听用户服务层实现
 */
@Service
public class ListenerServiceImpl implements IListenerService {
    @Autowired
    private ListenerMapper listenerMapper;

    /**
     * 查询用户
     *
     * @param userNum 用户账号
     * @return List<UserBean>
     */
    @Override
    public List<UserBean> selectAllUser(Integer userNum) {
        QueryWrapper<UserBean> wrapper = new QueryWrapper<>();
        wrapper.eq("member_account_number", userNum);
        return listenerMapper.selectList(wrapper);
    }
}
