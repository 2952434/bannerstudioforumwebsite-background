package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import studio.banner.forumwebsite.bean.UserBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/10/23 18:14
 * @role:
 */
public interface IAdminService {
    /**
     * 查询所有用户
     *
     * @param page 第几页
     * @return所有用户集合
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
