package studio.banner.forumwebsite.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Ljx
 * @Date: 2022/3/12 22:11
 * @role:
 */
public interface IRedisService {

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
    List<Map<String,String>> selectPostRank();

    /**
     * 每天自动更新用户15天增长浏览量
     * @return
     */
    void updateMemberViewNum();

    /**
     * 根据用户id查询15天每天浏览增长量
     * @param memberId
     * @return
     */
    List<Integer> selectEveryDayAddViewNum(Integer memberId);


    /**
     * 每天自动更新论坛15天增长浏览量
     * @return
     */
    void updateForumViewNum();

    /**
     * 查询论坛15天每天新增浏览量
     * @return
     */
    List<Integer> selectForumEveryDayAddViewNum();


    /**
     * 每天自动更新论坛15天增长发帖量
     * @return
     */
    void updateForumPostInsertNum();

    /**
     * 查询论坛15天每天新增发帖量
     * @return
     */
    List<Integer> selectForumPostInsertNum();

}
