package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.CollectFavoriteBean;

import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2022/3/7 23:37
 * @role: 收藏夹映射接口
 */
@Repository
public interface CollectFavoriteMapper extends BaseMapper<CollectFavoriteBean> {

    /**
     * 根据用户id查询默认收藏夹id
     * @param userId
     * @return
     */
    @Select("select fa.favorite_id from tab_collect co,tab_collect_favorite fa where fa.user_id=#{userId} and co.col_user_id = fa.user_id and fa.favorite_name='默认收藏夹'")
    Map<String,String> selectFavoriteId(@Param("userId") Integer userId);
}
