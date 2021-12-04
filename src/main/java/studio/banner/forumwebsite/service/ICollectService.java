package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.CollectBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/13 22:04
 */
public interface ICollectService {
    /**
     * 增加收藏文章
     *
     * @param collectBean
     * @return boolean
     */
    boolean insertCollect(CollectBean collectBean);

    /**
     * 根据id删除收藏文章
     *
     * @param id
     * @return boolean
     */
    boolean deleteCollect(Integer id);

    /**
     * 清除用户收藏
     *
     * @param userid
     * @return
     */
    boolean deleteCollectByUserId(Integer userid);

    /**
     * 根据不同用户id查询收藏文章
     *
     * @param userid
     * @return List
     */

    List<CollectBean> selectCollectByUserId(Integer userid);
}

