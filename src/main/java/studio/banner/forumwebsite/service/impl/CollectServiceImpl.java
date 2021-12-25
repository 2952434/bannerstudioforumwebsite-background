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
 * @role: 收藏服务层实现
 */
@Service
public class CollectServiceImpl implements ICollectService {
    @Autowired
    private CollectMapper collectMapper;

    /**
     * 增加收藏文章
     *
     * @param collectBean 贴子对象
     * @return boolean
     */
    @Override
    public boolean insertCollect(CollectBean collectBean) {
        return collectMapper.insert(collectBean) == 1;
    }

    /**
     * 根据id删除收藏文章
     *
     * @param id 用户id
     * @return boolean
     */
    @Override
    public boolean deleteCollect(Integer id) {
        return collectMapper.deleteById(id) == 1;
    }

    /**
     * 清除用户收藏
     *
     * @param userid 用户id
     * @return boolean
     */
    @Override
    public boolean deleteCollectByUserId(Integer userid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("clo_user_id", userid);
        if (collectMapper.deleteById(userid) != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据不同用户id查询收藏文章
     *
     * @param userid 用户id
     * @return List
     */
    @Override
    public List<CollectBean> selectCollectByUserId(Integer userid) {
        QueryWrapper<CollectBean> wrapper = new QueryWrapper<>();
        wrapper.eq("clo_user_id", userid);
        List<CollectBean> artTit = collectMapper.selectList(wrapper);
        return artTit;
    }
}
