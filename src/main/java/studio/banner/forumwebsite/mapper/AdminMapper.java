package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.CommentBean;
import studio.banner.forumwebsite.bean.UserBean;

/**
 * @Author: Ljx
 * @Date: 2021/10/23 18:11
 * @role:
 */
@Repository
public interface AdminMapper extends BaseMapper<UserBean> {
}
