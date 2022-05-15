package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.ReplyBean;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/17/17:42
 * @Description: 回复功能底层接口
 */
@Repository
public interface ReplyMapper extends BaseMapper<ReplyBean> {

    /**
     * 根据用户id查询回复评论信息
     * @param memberId
     * @param page
     * @return
     */
    @Select("select re.*,co.comment_content,me.member_head,me.member_name from tab_reply re,tab_comment co,tab_member_information me where re.reply_comment_member_id = #{memberId} and me.member_id = re.reply_member_id and re.comment_id = co.comment_id and re.reply_show = 0 order by re.reply_time desc limit #{page},15")
    List<Map<String,String>> selectReplyInformationById(@Param("memberId") Integer memberId,@Param("page") Integer page);
}
