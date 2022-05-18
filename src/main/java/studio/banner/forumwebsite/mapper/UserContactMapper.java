package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.UserAttentionBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: ljh
 * @Date: 2021/5/18 18:11
 * @role: 用户关联映射
 */
@Repository
public interface UserContactMapper extends BaseMapper<UserAttentionBean> {

    /**
     * 根据用户id查询其粉丝信息并分页
     * @param userId
     * @param page
     * @return
     */
    @Select("select me.*,at.* from tab_member_information me,tab_user_attention at where at.be_attention_id = #{userId} and at.attention_id = me.member_id order by at.contact_time desc limit #{page},12")
    List<Map<String,String>> selectFanByUserId(@Param("userId") Integer userId,@Param("page") Integer page);

    /**
     * 根据用户id查询其关注人信息并分页
     * @param userId
     * @param page
     * @return
     */
    @Select("select me.*,at.* from tab_member_information me,tab_user_attention at where at.attention_id = #{userId} and at.be_attention_id = me.member_id order by at.contact_time desc limit #{page},12")
    List<Map<String,String>> selectAttentionUserId(@Param("userId") Integer userId,@Param("page") Integer page);

    /**
     * 根据用户id查询其粉丝信息并分页
     * @param userId
     * @param page
     * @return
     */
    @Select("select me.*,at.* from tab_member_information me,tab_user_attention at where at.attention_show=0 and at.be_attention_id = #{userId} and at.attention_id = me.member_id order by at.contact_time desc limit #{page},12")
    List<Map<String, String>> selectAttentionInformation(@Param("userId") Integer userId,@Param("page") Integer page);
}
