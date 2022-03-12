package studio.banner.forumwebsite.service;

import org.springframework.data.redis.core.ZSetOperations;
import studio.banner.forumwebsite.bean.RespBean;

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
    Set<ZSetOperations.TypedTuple<String>> selectPostRank();

    /**
     * 每天自动更新用户15天增长浏览量
     * @return
     */
    void updateMemberLikeNum();

}
