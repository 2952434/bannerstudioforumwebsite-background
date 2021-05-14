package studio.banner.forumwebsite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.mapper.ListenerMapper;
import studio.banner.forumwebsite.service.IListenerService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/14 21:09
 */
@Service
public class ListenerServiceImpl implements IListenerService {
    @Autowired
    private ListenerMapper listenerMapper;
    @Override
    public List<UserBean> selectAllUser() {
        List<UserBean> list = listenerMapper.selectList(null);
        return list;
    }
}
