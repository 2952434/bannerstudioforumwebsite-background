package studio.banner.forumwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.MemberInformationBean;

/**
 * @Author: ljh
 * @Date: 2021/5/17 17:22
 * @role: 用户信息映射
 */
@Repository
public interface MemberInformationMapper extends BaseMapper<MemberInformationBean> {
}
