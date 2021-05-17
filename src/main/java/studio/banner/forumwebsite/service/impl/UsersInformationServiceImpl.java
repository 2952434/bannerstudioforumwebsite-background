package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.UsersInformationBean;
import studio.banner.forumwebsite.mapper.UsersInformationMapper;
import studio.banner.forumwebsite.service.IUsersInformationService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/15 16:08
 */
@Service
public class UsersInformationServiceImpl implements IUsersInformationService {

    @Autowired
    private UsersInformationMapper usersInformationMapper;

    @Override
    public boolean insertUsersInformation(UsersInformationBean usersInformationBean) {
        return usersInformationMapper.insert(usersInformationBean) == 1;
    }

    @Override
    public List<UsersInformationBean> selectUsersInformation() {
        List<UsersInformationBean> list = usersInformationMapper.selectList(null);
        return list;
    }

    @Override
    public boolean deleteUsersInformation(Integer id) {
        return usersInformationMapper.deleteById(id) == 1;
    }

    @Override
    public boolean updateUsersInformation(UsersInformationBean usersInformationBean) {
        QueryWrapper<UsersInformationBean> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",usersInformationBean.getUserId());
        return usersInformationMapper.update(usersInformationBean,wrapper) == 1;
    }


    @Override
    public IPage<UsersInformationBean> selectUsersInformationBeanPage(Integer page) {
        Page<UsersInformationBean> page1 = new Page<>(page,10);
        IPage<UsersInformationBean> page2 =usersInformationMapper.selectPage(page1,null);
        return page2;
    }


}
