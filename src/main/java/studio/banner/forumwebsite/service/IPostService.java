package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.redis.core.ZSetOperations;
import studio.banner.forumwebsite.bean.PostBean;

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
     * @param postBean
     * @return boolean
     */
    boolean insertPost (PostBean postBean);

    /**
     * 转发帖子
     * @param postBean
     * @return boolean
     */
    boolean forwardPost(PostBean postBean);

    /**
     * 根据帖子id删除帖子
     * @param postId
     * @return boolean
     */
    boolean deletePost(int postId);

    /**
     * 根据用户id删除用户全部帖子
     * @param postMemberId
     * @return boolean
     */

    boolean deleteAllPost(int postMemberId);

    /**
     * 根据帖子id更改帖子标题
     * @param postId
     * @return boolean
     */

    boolean updatePostTitle(int postId, String newTitle);


    /**
     * 根据帖子id更改帖子内容
     * @param postId
     * @param newContent
     * @return boolean
     */

    boolean updatePostContent(int postId , String newContent);

    /**
     * 根据帖子id更改浏览量
     * @param postId
     * @return boolean
     */

    boolean updatePostpageview(int postId);

    /**
     * 根据帖子id更改评论量
     * @param postId
     * @return boolean
     */

    boolean updatePostCommentNumber(int postId);

    /**
     * 根据帖子id更改点赞量
     * @param postId
     * @return boolean
     */

    boolean updatePostLikeNumber(int postId);

    /**
     * 根据帖子id查询帖子
     * @param postId
     * @return PostBean
     */

    PostBean selectPost(int postId);

    /**
     * 根据用户id查询某用户全部帖子
     * @param postMemberId
     * @return List
     */

    List<PostBean> selectAllPostById(int postMemberId);

    /**
     * 分页查询所有帖子
     * @param page
     * @return IPage
     */
    IPage<PostBean> selectAllPost(int page);

    /**
     * 模糊查询帖子和作者
     * @param page 第几页
     * @param dim 查询字段
     * @return
     */
    IPage<PostBean> selectDimPost(int page,String dim);

//    /**
//     * 将数据库中的帖子导入到Redis中
//     *
//     * @return
//     */
//    Set<ZSetOperations.TypedTuple<String>> addRedis();

    /**
     * 每天早上1点自动更新Redis数据库中的帖子排名
     */
    void updateRedisPostRank();

    /**
     * 帖子排行榜查询
     * @return
     */
    Set<ZSetOperations.TypedTuple<String>> selectPostRank();

}
