package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.PostBean;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/08/23:13
 * @Description:帖子底层接口
 */

@Repository
public interface PostMapper extends BaseMapper<PostBean> {

    /**
     * 查询帖子实体
     * @return
     */
    @Select("select po.*,me.member_name,me.member_head from tab_post po,tab_member_information me where po.post_member_id = me.member_id")
    List<Map<String,String>> selectListPost();

    /**
     * 根据用户id查询帖子信息
     * @param postId
     * @return
     */
    @Select("select po.*,me.member_name,me.member_head from tab_post po,tab_member_information me where po.post_member_id = me.member_id and po.post_id=#{postId}")
    List<Map<String,String>> selectPostById(Integer postId);
}
