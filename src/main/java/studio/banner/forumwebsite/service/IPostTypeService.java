package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * @param postId
     * @return
     */
    String[] selectPostTypeById(Integer postId);

    /**
     * 增加帖子类型
     * @param postType
     * @return
     */
    boolean insertPostType(PostTypeBean postType);

    /**
     * 查询所有帖子内容
     * @return
     */
    List<PostTypeBean> selectAllPostType();

    /**
     * 删除帖子类型
     * @param postType
     * @return
     */
    boolean deletePostType(String postType);

    /**
     * 通过类型分页查询该类型的帖子
     * @param postType
     * @return
     */
    List<PostBean> selectPostByType(String postType);
}
