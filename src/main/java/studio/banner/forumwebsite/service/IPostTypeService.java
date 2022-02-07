package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.PostTypeBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 14:45
 * @role: 帖子类型操作接口
 */
public interface IPostTypeService {
    /**
     * 通过帖子id查询帖子所属类型
     *
     * @param postId 帖子id
     * @return String[]
     */
    String[] selectPostTypeById(Integer postId);

    /**
     * 增加帖子类型
     *
     * @param postType 帖子类型
     * @return boolean
     */
    boolean insertPostType(PostTypeBean postType);

    /**
     * 查询所有帖子类型
     *
     * @return List<PostTypeBean>
     */
    List<PostTypeBean> selectAllPostType();

    /**
     * 删除帖子类型
     *
     * @param postType 帖子类型
     * @return boolean
     */
    boolean deletePostType(String postType);

    /**
     * 通过类型分页查询该类型的帖子
     *
     * @param postType 帖子类型
     * @return List<PostBean>
     */
    List<PostBean> selectPostByType(String postType);

    /**
     * 通过类型分页查询该类型的帖子根据浏览量逆向排序
     *
     * @param postType 帖子类型
     * @return List<PostBean>
     */
    List<PostBean> selectPostDescByType(String postType);



}
