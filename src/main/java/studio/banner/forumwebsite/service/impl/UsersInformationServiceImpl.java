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

    /**
     * 增加用户信息到信息表
     *
     * @param usersInformationBean 用户信息实体
     * @return boolean
     */
    @Override
    public boolean insertUsersInformation(UsersInformationBean usersInformationBean) {
        return usersInformationMapper.insert(usersInformationBean) == 1;
    }

    /**
     * 查询用户信息通过id
     *
     * @param id 用户id
     * @return boolean
     */
    @Override
    public boolean selectUsersInformationById(Integer id) {
        QueryWrapper<UsersInformationBean> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List list = usersInformationMapper.selectList(wrapper);
        if (list.size() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 删除用户信息
     *
     * @param id 用户id
     * @return boolean
     */
    @Override
    public boolean deleteUsersInformation(Integer id) {
        QueryWrapper<UsersInformationBean> wrapper = new QueryWrapper();
        wrapper.eq("user_id", id);
        return usersInformationMapper.delete(wrapper) == 1;
    }

    /**
     * 更改用户信息
     *
     * @param usersInformationBean 用户信息实体
     * @return boolean
     */
    @Override
    public boolean updateUsersInformation(UsersInformationBean usersInformationBean) {
        QueryWrapper<UsersInformationBean> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", usersInformationBean.getUserId());
        if (usersInformationMapper.update(usersInformationBean, wrapper) != 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 分页查询
     *
     * @param page 页数
     * @return IPage<UsersInformationBean>
     */
    @Override
    public IPage<UsersInformationBean> selectUsersInformationBeanPage(Integer page) {
        Page<UsersInformationBean> page1 = new Page<>(page, 10);
        IPage<UsersInformationBean> page2 = usersInformationMapper.selectPage(page1, null);
        return page2;
    }

    /**
     * 模糊查询用户
     *
     * @param page 第几页
     * @param dim  模糊查询字段
     * @return IPage<UsersInformationBean>
     */
    @Override
    public IPage<UsersInformationBean> selectUserInformationDimPage(Integer page, String dim) {
        Page<UsersInformationBean> page1 = new Page<>(page, 10);
        QueryWrapper<UsersInformationBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("users_name", dim)
                .or().like("users_direction", dim)
                .or().like("users_phone", dim)
                .or().like("users_qq", dim)
                .or().like("users_wechat", dim)
                .or().like("users_company", dim)
                .or().like("users_work", dim)
                .or().like("users_address", dim)
                .or().like("users_pay", dim);
        IPage<UsersInformationBean> iPage = usersInformationMapper.selectPage(page1, queryWrapper);
        return iPage;
    }
}
