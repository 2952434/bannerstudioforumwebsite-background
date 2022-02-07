package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.redis.core.ZSetOperations;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.PostBeanEs;

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
     *
     * @param postBean 帖子实体
     * @param postType 帖子类型
     * @return boolean
     */
    boolean insertPost(PostBean postBean,String postGrade, String... postType);

    /**
     * 根据帖子id删除帖子
     *
     * @param postId 帖子id
     * @return boolean
     */
    boolean deletePost(int postId);

    /**
     * 根据用户id删除用户全部帖子
     *
     * @param postMemberId 用户id
     * @return boolean
     */
    boolean deleteAllPost(int postMemberId);

    /**
     * 根据帖子id更改帖子标题
     *
     * @param postId   帖子id
     * @param newTitle 新标题
     * @return boolean
     */

    boolean updatePostTitle(int postId, String newTitle);


    /**
     * 根据帖子id更改帖子内容
     *
     * @param postId     帖子id
     * @param newContent 新内容
     * @return boolean
     */

    boolean updatePostContent(int postId, String newContent);

    /**
     * 根据帖子id更改浏览量
     *
     * @param postId 帖子id
     * @return boolean
     */

    boolean updatePostPageView(int postId);

    /**
     * 根据帖子id更改评论量
     *
     * @param postId 帖子id
     * @return boolean
     */

    boolean updatePostCommentNumber(int postId);

    /**
     * 根据帖子id更改点赞量
     *
     * @param postId 帖子id
     * @return boolean
     */

    boolean updatePostLikeNumber(int postId);

    /**
     * 根据帖子id查询帖子
     *
     * @param postId 帖子id
     * @return PostBean
     */

    PostBean selectPost(int postId);

    /**
     * 根据用户id查询某用户全部帖子(根据时间返向排序)
     *
     * @param postMemberId 用户id
     * @return List
     */

    List<PostBean> selectAllPostByDescById(int postMemberId);

    /**
     * 根据用户id查询某用户全部帖子(根据时间正向排序)
     *
     * @param postMemberId 用户id
     * @return List
     */

    List<PostBean> selectAllPostByAscById(int postMemberId);

    /**
     * 分页查询所有帖子
     *
     * @param page 页数
     * @return IPage
     */
    IPage<PostBean> selectAllPost(int page);

    /**
     * 全文检索帖子和作者
     *
     * @param page 第几页
     * @param dim  查询字段
     * @return List<PostBeanEs>
     */
    List<PostBeanEs> selectDimPost(int page, String dim);

    /**
     * 将数据库中的帖子导入到Redis中
     *
     * @return Set<ZSetOperations.TypedTuple < String>>
     */
    Set<ZSetOperations.TypedTuple<String>> addRedis();

    /**
     * 每天早上1点自动更新Redis数据库中的帖子排名
     *
     * @return Set<ZSetOperations.TypedTuple < String>>
     */
    Set<ZSetOperations.TypedTuple<String>> updateRedisPostRank();

    /**
     * 帖子排行榜查询
     *
     * @return Set<ZSetOperations.TypedTuple < String>>
     */
    Set<ZSetOperations.TypedTuple<String>> selectPostRank();

    /**
     * 每分钟更新一次es中的数据
     */
    void updateEsPost();

    /**
     * 根据作者id查询昨天的总浏览量
     *
     * @param memberId 用户id
     * @return String
     */
    String selectYesterdayView(Integer memberId);

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
