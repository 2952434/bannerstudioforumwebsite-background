package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.FixedInformationBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.mapper.FixedInformationMapper;
import studio.banner.forumwebsite.service.IFixedInformationService;

import java.util.List;


/**
 * @Author: Ljx
 * @Date: 2021/5/15 16:08
 */
@Service
public class FixedInformationServiceImpl implements IFixedInformationService {

    protected static Logger logger = LoggerFactory.getLogger(FixedInformationServiceImpl.class);

    @Autowired
    private FixedInformationMapper fixedInformationMapper;

    /**
     * 增加用户信息到信息表
     *
     * @param fixedInformationBean 用户信息实体
     * @return boolean
     */
    @Override
    public boolean insertUsersInformation(FixedInformationBean fixedInformationBean) {
        if (selectUsersInformationById(fixedInformationBean.getUserId())!=null){
            if (updateUsersInformation(fixedInformationBean)){
                logger.info("用户信息更新成功");
                return true;
            }else {
                logger.error("用户信息更新失败");
                return false;
            }
        }else {
            if (fixedInformationMapper.insert(fixedInformationBean) == 1){
                logger.info("用户信息插入成功");
                return true;
            }else {
                logger.error("用户信息插入失败");
                return false;
            }
        }
    }

    /**
     * 查询用户信息通过id
     *
     * @param id 用户id
     * @return boolean
     */
    @Override
    public FixedInformationBean selectUsersInformationById(Integer id) {
        QueryWrapper<FixedInformationBean> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        return fixedInformationMapper.selectOne(wrapper);
    }

    /**
     * 删除用户信息
     *
     * @param id 用户id
     * @return boolean
     */
    @Override
    public boolean deleteUsersInformation(Integer id) {
        QueryWrapper<FixedInformationBean> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        return fixedInformationMapper.delete(wrapper) == 1;
    }

    /**
     * 更改用户信息
     *
     * @param fixedInformationBean 用户信息实体
     * @return boolean
     */
    @Override
    public boolean updateUsersInformation(FixedInformationBean fixedInformationBean) {
        QueryWrapper<FixedInformationBean> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", fixedInformationBean.getUserId());
        return fixedInformationMapper.update(fixedInformationBean, wrapper) == 1;
    }

    @Override
    public RespBean selectAllUserInformation() {
        List<FixedInformationBean> fixedInformationBeans = fixedInformationMapper.selectList(null);
        return RespBean.ok("查询成功",fixedInformationBeans);
    }

}
