package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.InformBean;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.mapper.InformMapper;
import studio.banner.forumwebsite.service.IInformService;

/**
 * @Author: Ljx
 * @Date: 2021/12/19 14:10
 * @role:
 */
@Service
public class InformServiceImpl implements IInformService {

    @Autowired
    private InformMapper informMapper;

    @Override
    public boolean insertInform(InformBean informBean) {
        int insert = informMapper.insert(informBean);
        return insert==1;
    }

    @Override
    public IPage<InformBean> selectInform(Integer page) {
        QueryWrapper<InformBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("inform_time");
        Page<InformBean> page1 = new Page<>(page, 6);
        Page<InformBean> informBeanPage = informMapper.selectPage(page1, queryWrapper);
        return informBeanPage;
    }

    @Override
    public InformBean selectInformById(Integer id) {
        InformBean informBean = informMapper.selectById(id);
        return informBean;
    }

    @Override
    public boolean updateInformById(InformBean informBean) {
        int updateById = informMapper.updateById(informBean);
        return updateById==1;
    }

    @Override
    public boolean deleteInformById(Integer id) {
        int delete = informMapper.deleteById(id);
        return delete==1;
    }
}
