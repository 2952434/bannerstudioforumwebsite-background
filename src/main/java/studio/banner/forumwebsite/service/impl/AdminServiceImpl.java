package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.mapper.AdminMapper;
import studio.banner.forumwebsite.mapper.PostMapper;
import studio.banner.forumwebsite.service.IAdminService;

import java.util.List;


/**
 * @Author: Ljx
 * @Date: 2021/10/23 18:20
 * @role:
 */
@Service
public class AdminServiceImpl implements IAdminService {

   @Autowired
   private AdminMapper adminMapper;

   @Autowired
   private PostMapper postMapping;

    /**
     * 查询用户
     * @param page 第几页
     * @return page
     */
    @Override
    public Page<UserBean> selectAllUser(int page) {
        QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_admin",0);
        Page<UserBean> page1 = new Page<>(page,10);
        Page<UserBean> page2 = adminMapper.selectPage(page1, queryWrapper);
        return page2;
    }

    /**
     * 根据id删除用户
     * @param id 用户id
     * @return boolean
     */
    @Override
    public boolean deleteById(int id) {
        QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_admin",1);
        QueryWrapper<PostBean> postBeanQueryWrapper = new QueryWrapper<>();
        postBeanQueryWrapper.eq("post_member_id",id);
        List<UserBean> userBeans = adminMapper.selectList(queryWrapper);
            if (adminMapper.deleteById(id)==1&&userBeans.size()==0){
                postMapping.delete(postBeanQueryWrapper);
                return true;
            }else {
                return false;
        }
    }
}
