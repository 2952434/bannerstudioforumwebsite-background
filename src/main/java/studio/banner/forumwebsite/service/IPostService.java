package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.redis.core.ZSetOperations;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.PostEsBean;
import studio.banner.forumwebsite.bean.RespBean;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/08/23:15
 * @Description: 帖子操作的接口
 */
public interface IPostService {

    /**
     * 增加帖子
     * @param postBean 帖子实体
     * @return
     */
    RespBean insertPost(PostBean postBean);

    /**
     * 根据帖子id删除帖子
     *
     * @param postId 帖子id
     * @return boolean
     */
    RespBean deletePostById(Integer postId);

    /**
     * 根据用户id删除用户全部帖子
     *
     * @param postMemberId 用户id
     * @return boolean
     */
    RespBean deleteAllPost(Integer postMemberId);

    /**
     * 修改帖子内容
     * @param postBean
     * @return
     */
    RespBean updatePostById(PostBean postBean);

    /**
     * 根据帖子id更改浏览量
     *
     * @param postId 帖子id
     * @return boolean
     */

    boolean updatePostPageView(Integer postId);

    /**
     * 根据帖子id更改评论量
     *
     * @param postId 帖子id
     * @return boolean
     */

    boolean updatePostCommentNumber(Integer postId);

    /**
     * 根据帖子id更改点赞量
     *
     * @param postId 帖子id
     * @return boolean
     */

    boolean updatePostLikeNumber(Integer postId);

    /**
     * 通过帖子id更改收藏量
     * @param postId
     * @return
     */
    boolean updatePostColNumByPostId(Integer postId);

    /**
     * 根据帖子id查询帖子
     *
     * @param postId 帖子id
     * @return PostBean
     */

    PostBean selectPost(Integer postId);

    /**
     * 通过用户id查询其帖子数量
     * @param memberId
     * @return
     */
    Integer selectPostNumByMemberId(Integer memberId);

    /**
     * 根据用户id查询某用户全部帖子(根据时间返向排序)
     *
     * @param postMemberId 用户id
     * @return List
     */

    List<PostBean> selectAllPostByDescById(Integer postMemberId);

    /**
     * 根据用户id查询某用户全部帖子(根据时间正向排序)
     *
     * @param postMemberId 用户id
     * @return List
     */

    List<PostBean> selectAllPostByAscById(Integer postMemberId);

    /**
     * 查询所有帖子数量
     * @return
     */
    Integer selectAllPostNum();
    /**
     * 分页查询所有帖子
     *
     * @param page 页数
     * @return IPage
     */
    IPage<PostBean> selectAllPost(Integer page);



    /**
     * 根据帖子id实现置顶功能
     *
     * @param postId 帖子id
     * @return boolean
     */
    boolean updatePostTopById(Integer postId);

    /**
     * 根据贴子id取消置顶
     *
     * @param postId 帖子id
     * @return boolean
     */
    boolean updatePostNoTopById(Integer postId);
}
