package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.ReplyBean;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/17/17:42
 * @Description: 回复功能底层接口
 */
@Repository
public interface ReplyMapper extends BaseMapper<ReplyBean> {
}
