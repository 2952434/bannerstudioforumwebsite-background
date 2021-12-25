package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.UserGradeContactBean;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 20:00
 * @role: 用户年级、姓名、方向关联映射
 */
@Repository
public interface UserGradeContactMapper extends BaseMapper<UserGradeContactBean> {
}
