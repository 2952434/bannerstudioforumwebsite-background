package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.CollectBean;
import studio.banner.forumwebsite.mapper.CollectMapper;
import studio.banner.forumwebsite.service.ICollectService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 22:05
 */
@Service
public class CollectServiceImpl implements ICollectService {
    @Autowired
    private CollectMapper collectMapper;

    @Override
    public boolean insertCollect(CollectBean collectBean) {
        return collectMapper.insert(collectBean) == 1;
    }

    @Override
    public boolean deleteCollect(Integer id) {
        return collectMapper.deleteById(id) ==1;
    }

    @Override
    public List<CollectBean> selectCollectByUserId(Integer userid) {
        QueryWrapper<CollectBean> wrapper = new QueryWrapper<>();
        wrapper.eq("clo_user_id",userid);
        List<CollectBean> artTit = collectMapper.selectList(wrapper);
        return artTit;
    }
}
