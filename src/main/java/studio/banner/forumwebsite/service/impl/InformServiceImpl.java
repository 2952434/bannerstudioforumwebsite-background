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
 * @role: 通知服务层实现
 */
@Service
public class InformServiceImpl implements IInformService {

    @Autowired
    private InformMapper informMapper;

    /**
     * 增加通知
     *
     * @param informBean 通知实体
     * @return boolean
     */
    @Override
    public boolean insertInform(InformBean informBean) {
        int insert = informMapper.insert(informBean);
        return insert == 1;
    }

    /**
     * 分页查询通知
     *
     * @param page 页数
     * @return IPage<InformBean>
     */
    @Override
    public IPage<InformBean> selectInform(Integer page) {
        QueryWrapper<InformBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("inform_time");
        Page<InformBean> page1 = new Page<>(page, 6);
        Page<InformBean> informBeanPage = informMapper.selectPage(page1, queryWrapper);
        return informBeanPage;
    }

    /**
     * 通过id查询通知
     *
     * @param id 通知id
     * @return InformBean
     */
    @Override
    public InformBean selectInformById(Integer id) {
        InformBean informBean = informMapper.selectById(id);
        return informBean;
    }

    /**
     * 更新通知内容
     *
     * @param informBean 通知实体
     * @return boolean
     */
    @Override
    public boolean updateInformById(InformBean informBean) {
        int updateById = informMapper.updateById(informBean);
        return updateById == 1;
    }

    /**
     * 根据id删除通知
     *
     * @param id 通知id
     * @return boolean
     */
    @Override
    public boolean deleteInformById(Integer id) {
        int delete = informMapper.deleteById(id);
        return delete == 1;
    }
}
