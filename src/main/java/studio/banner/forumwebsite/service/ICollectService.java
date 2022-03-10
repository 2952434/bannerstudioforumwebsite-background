package studio.banner.forumwebsite.service;

import studio.banner.forumwebsite.bean.CollectBean;
import studio.banner.forumwebsite.bean.CollectFavoriteBean;
import studio.banner.forumwebsite.bean.RespBean;

import java.util.List;


/**
 * @Author: Ljx
 * @Date: 2022/3/7 23:38
 * @role: 收藏夹服务层接口
 */
public interface ICollectService {

    /**
     * 添加收藏夹
     * @param collectFavoriteBean
     * @return
     */
    RespBean insertCollectFavorite(CollectFavoriteBean collectFavoriteBean);

    /**
     * 根据用户id 和 收藏夹名判断收藏夹是否存在
     * @param userId
     * @param collectFavoriteName
     * @return
     */
    CollectFavoriteBean judgeCollectFavorite(Integer userId,String collectFavoriteName);

    /**
     * 通过用户id查询其收藏文章数量
     * @param userId
     * @return
     */
    Integer selectCollectNumByUserId(Integer userId);

    /**
     * 通过帖子id查询其收藏量
     * @param postId
     * @return
     */
    Integer selectCollectNumByPostId(Integer postId);
    /**
     * 更新收藏夹
     * @param collectFavoriteBean
     * @return
     */
    RespBean updateCollectFavorite(CollectFavoriteBean collectFavoriteBean);

    /**
     * 根据用户id 和查看人id 查看用户收藏夹
     * @param userId
     * @param selectId
     * @return
     */
    RespBean selectCollectFavoriteById(Integer userId,Integer selectId);

    /**
     * 删除收藏夹
     * @param favoriteId
     * @param moveFavoriteId
     * @return
     */
    RespBean deleteCollectFavorite(Integer favoriteId,Integer moveFavoriteId);

    /**
     * 判断该用户是否收藏该帖子
     * @param userId
     * @param postId
     * @return
     */
    boolean judgeCollectPost(Integer userId,Integer postId);

    /**
     * 增加收藏文章
     *
     * @param collectBean 贴子对象
     * @return boolean
     */
    boolean insertCollect(CollectBean collectBean);

    /**
     * 根据收藏帖子id和用户id取消收藏文章
     * @param postId
     * @param userId
     * @return
     */
    boolean deleteCollect(Integer postId,Integer userId);

    /**
     * 通过收藏id批量取消收藏
     * @param ids
     * @return
     */
    RespBean deleteBatchCollectByIds(List<Integer> ids);


    /**
     * 清除用户收藏
     *
     * @param userid 用户id
     * @return boolean
     */
    boolean deleteCollectByUserId(Integer userid);

    /**
     * 通过收藏帖子id 收藏夹id 更改帖子收藏夹
     * @param colId
     * @param favoriteId
     * @return
     */
    RespBean updateCollectById(Integer colId,Integer favoriteId);

    /**
     * 通过收藏帖子id 收藏夹id 批量更改帖子收藏夹
     * @param colIds
     * @param favoriteId
     * @return
     */
    RespBean updateCollectByIds(List<Integer> colIds, Integer favoriteId);

    /**
     * 根据收藏夹id查询收藏夹收藏帖子
     * @param favoriteId
     * @return
     */
    RespBean selectCollectByFavoriteId(Integer favoriteId);

}
