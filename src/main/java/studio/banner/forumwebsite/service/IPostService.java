package studio.banner.forumwebsite.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.redis.core.ZSetOperations;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.PostBeanEs;
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
     * 全文检索帖子和作者
     *
     * @param page 第几页
     * @param dim  查询字段
     * @return List<PostBeanEs>
     */
    List<PostBeanEs> selectDimPost(Integer page, String dim);

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
