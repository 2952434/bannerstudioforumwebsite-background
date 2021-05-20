package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.CommentBean;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/15/17:00
 * @Description:评论底层接口
 */
@Repository
public interface CommentMapper extends BaseMapper<CommentBean> {
}
