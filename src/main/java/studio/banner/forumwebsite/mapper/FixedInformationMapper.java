package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.FixedInformationBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2021/5/15 16:06
 * @role: 所有用户信息映射
 */
@Repository
public interface FixedInformationMapper extends BaseMapper<FixedInformationBean> {

    /**
     * 获取每个方向的人数
     * @return Map<String,Integer>
     */
    @Select("select users_direction name,count(*) value from tab_fixed_information group by users_direction;")
    List<HashMap<String,String>> selectDirectionNum();

    /**
     * 查询每个方向发帖数量
     * @return List<HashMap<String,String>>
     */
    @Select("select f.users_direction name,count(p.post_id != '') value from tab_fixed_information f left join tab_post p on f.user_id = p.post_member_id group by f.users_direction ;")
    List<HashMap<String,String>> selectDirectionPostNum();
}
