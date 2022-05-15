package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.CommentBean;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/15/17:00
 * @Description: 评论底层接口
 */
@Repository
public interface CommentMapper extends BaseMapper<CommentBean> {

    /**
     * 根据用户id查询回复信息
     * @param memberId
     * @param page
     * @return
     */
    @Select("select co.*,me.member_head,me.member_name,po.post_title from tab_comment co,tab_member_information me,tab_post po where co.comment_member_id = me.member_id and co.comment_post_id = po.post_id and co.post_member_id = #{memberId} and co.comment_show = 0 order by co.comment_time desc limit #{page},15")
    List<Map<String,String>> selectCommentByMemberId(@Param("memberId") Integer memberId,@Param("page") Integer page);
}
