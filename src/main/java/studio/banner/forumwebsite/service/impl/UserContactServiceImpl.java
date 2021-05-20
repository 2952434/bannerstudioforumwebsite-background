package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.UserContactBean;
import studio.banner.forumwebsite.mapper.UserContactMapper;
import studio.banner.forumwebsite.service.IUserContactService;

import java.util.List;

/**
 * @Author: Mo
 * @Date: 2021/5/18 18:35
 */
@Service
public class UserContactServiceImpl implements IUserContactService {
    protected static Logger logger = LoggerFactory.getLogger(UserMsgServiceImpl.class);

    @Autowired
    private UserContactMapper userContactMapper;

    /**
     * 将关注人fan与被关注人star存入关系表，实现用户间的关注
     * @param userContactBean
     * @return boolean
     */
    @Override
    public boolean insertContact(UserContactBean userContactBean) {
        if (userContactBean.getMemberStar() != null){
            userContactMapper.insert(userContactBean);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 取消关注
     * @param attentionId
     * @return boolean
     */
    @Override
    public boolean deleteContact(Integer attentionId) {
        QueryWrapper<UserContactBean> wrapper = new QueryWrapper<>();
        wrapper.eq("attentionId",attentionId);
        boolean cancel = (userContactMapper.deleteById(attentionId) != 0);
        return cancel;
    }

    /**
     * 查询是否存在关注关系
     * @param memberFan
     * @param memberStar
     * @return list
     */
    @Override
    public List<UserContactBean> contacted(Integer memberFan,Integer memberStar) {
        QueryWrapper<UserContactBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_fan", memberFan)
                .eq("member_star",memberStar);
        List<UserContactBean> list = userContactMapper.selectList(queryWrapper);
        return list;
    }
}
