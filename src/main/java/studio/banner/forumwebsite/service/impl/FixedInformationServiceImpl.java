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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * 根据用户账号查询是否存在该用户
     * @param account
     * @return
     */
    @Override
    public FixedInformationBean selectUsersInformationByAccount(String account) {
        QueryWrapper<FixedInformationBean> wrapper = new QueryWrapper<>();
        wrapper.eq("users_account", account);
        return fixedInformationMapper.selectOne(wrapper);
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

    /**
     * 查询所有用户信息
     * @return
     */
    @Override
    public RespBean selectAllUserInformation() {
        List<FixedInformationBean> fixedInformationBeans = fixedInformationMapper.selectList(null);
        return RespBean.ok("查询成功",fixedInformationBeans);
    }

    /**
     * 获取每个方向的人数
     * @return List<HashMap<String,String>>
     */
    @Override
    public List<HashMap<String, String>> selectDirectionNum() {
        return fixedInformationMapper.selectDirectionNum();
    }

    /**
     * 查询每个方向的发帖数量
     * @return List<HashMap<String,String>>
     */
    @Override
    public List<HashMap<String, String>> selectDirectionPostNum() {
        return fixedInformationMapper.selectDirectionPostNum();
    }


}
