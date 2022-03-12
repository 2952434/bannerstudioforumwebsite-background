package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.PostTypeBean;
import studio.banner.forumwebsite.bean.RespBean;

/**
 * @Author: Ljx
 * @Date: 2022/3/11 14:41
 * @role:
 */
public interface IPostTypeService {


    /**
     * 通过帖子名查询是否存在该类型
     * @param name
     * @return
     */
    boolean selectPostTypeByName(String name);

    /**
     * 增加帖子类型
     * @param postTypeBean
     * @return
     */
    RespBean insertPostType(PostTypeBean postTypeBean);

    /**
     * 根据类型id删除该类型
     * @param typeId
     * @return
     */
    RespBean deletePostTypeById(Integer typeId);

    /**
     * 查询所有帖子类型
     * @return
     */
    RespBean selectPostAllType();
}
