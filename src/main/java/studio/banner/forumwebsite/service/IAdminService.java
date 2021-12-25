package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import studio.banner.forumwebsite.bean.UserBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/10/23 18:14
 * @role: 管理员服务层接口
 */
public interface IAdminService {
    /**
     * 查询所有用户
     *
     * @param page 第几页
     * @return Page<UserBean>
     */
    Page<UserBean> selectAllUser(int page);

    /**
     * 根据用户id删除用户
     *
     * @param id 用户id
     * @return boolean
     */
    boolean deleteById(int id);


}
