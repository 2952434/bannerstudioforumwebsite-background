package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.MemberInformationBean;

import java.util.Map;

/**
 * @Author: ljh
 * @Date: 2021/5/17 17:22
 * @role: 用户信息映射
 */
@Repository
public interface MemberInformationMapper extends BaseMapper<MemberInformationBean> {

    /**
     * 根据memberId查询用户所有信息
     * @param memberId 用户id
     * @return Map<String,String>
     */
    @Select("select me.*,fi.* from tab_member_information me,tab_fixed_information fi where me.member_id = #{memberId} and me.member_id = fi.user_id")
    Map<String,String> selectAllInformationByMemberId(Integer memberId);

    /**
     * 更新粉丝数
     * @param beAttentionId 被关注者id
     * @param num 粉丝数
     * @return
     */
    @Update("UPDATE tab_member_information SET member_fans= #{num} WHERE (member_id = #{beAttentionId})")
    Integer updateMemberFansInteger(@Param("beAttentionId") Integer beAttentionId,@Param("num") Integer num);
}
