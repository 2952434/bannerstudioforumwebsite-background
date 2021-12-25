package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.PostContactBean;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 14:56
 * @role: 帖子类型关联映射
 */
@Repository
public interface PostContactMapper extends BaseMapper<PostContactBean> {
}
