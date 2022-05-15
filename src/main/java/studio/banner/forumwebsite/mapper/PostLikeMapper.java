package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.PostLikeBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2022/3/10 20:10
 * @role:
 */
@Repository
public interface PostLikeMapper extends BaseMapper<PostLikeBean> {


    /**
     * 通过用户id分页查询点赞信息
     * @param userId
     * @param page
     * @return
     */
    @Select("select me.member_head,me.member_name,po.post_title,li.* from tab_post po,tab_post_like li,tab_member_information me where li.user_like_id=me.member_id and li.be_user_like_id = #{userId} and li.like_show = 0 and li.like_post_id = po.post_id order by li.like_time desc limit #{page},15")
    List<Map<String,String>> selectPostLikeByUserId(@Param("userId") Integer userId,@Param("page") Integer page);
}
