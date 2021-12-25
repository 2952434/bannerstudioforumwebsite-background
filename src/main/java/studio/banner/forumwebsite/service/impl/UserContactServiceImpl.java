package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.UserContactBean;
import studio.banner.forumwebsite.mapper.UserContactMapper;
import studio.banner.forumwebsite.mapper.UserMsgMapper;
import studio.banner.forumwebsite.service.IUserContactService;

import java.util.List;

/**
 * @Author: Mo
 * @Date: 2021/5/18 18:35
 * @role: 关注关系服务实现
 */
@Service
public class UserContactServiceImpl implements IUserContactService {
    protected static Logger logger = LoggerFactory.getLogger(UserMsgServiceImpl.class);

    @Autowired
    private UserContactMapper userContactMapper;

    /**
     * 新增关注
     *
     * @param userContactBean 关注实体
     * @return boolean
     */
    @Override
    public boolean insertContact(UserContactBean userContactBean) {
        if (userContactBean.getMemberStar() != null) {
            userContactMapper.insert(userContactBean);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取消关注
     *
     * @param attentionId 关注id
     * @return boolean
     */
    @Override
    public boolean deleteContact(Integer attentionId) {
        QueryWrapper<UserContactBean> wrapper = new QueryWrapper<>();
        wrapper.eq("attentionId", attentionId);
        boolean cancel = (userContactMapper.deleteById(attentionId) != 0);
        return cancel;
    }

    /**
     * 查询是否存在关注关系，返回对象
     *
     * @param memberFan  粉丝id
     * @param memberStar 被关注者id
     * @return List<UserContactBean>
     */
    @Override
    public List<UserContactBean> contacted(Integer memberFan, Integer memberStar) {
        QueryWrapper<UserContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_fan", memberFan)
                .eq("member_star", memberStar);
        List<UserContactBean> list = userContactMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 根据用户Id查询其粉丝
     *
     * @param memberStar 用户id
     * @return List<UserContactBean>
     */
    @Override
    public List<UserContactBean> fans(Integer memberStar) {
        QueryWrapper<UserContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_star", memberStar);
        List<UserContactBean> list = userContactMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 根据Id查询其关注的人
     *
     * @param memberFan 粉丝id
     * @return List<UserContactBean>
     */
    @Override
    public List<UserContactBean> stars(Integer memberFan) {
        QueryWrapper<UserContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_fan", memberFan);
        List<UserContactBean> list = userContactMapper.selectList(queryWrapper);
        return list;
    }
}
