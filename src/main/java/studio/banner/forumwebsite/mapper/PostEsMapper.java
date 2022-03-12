package studio.banner.forumwebsite.mapper;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.PostEsBean;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/3 21:29
 * @role: es帖子映射
 */
@Repository
public interface PostEsMapper extends ElasticsearchRepository<PostEsBean, Integer> {
    /**
     * 根据标题、内容和帖子类型查询（含分页）
     * @param title
     * @param context
     * @param postType
     * @param pageable
     * @return
     */
    List<PostEsBean> findByPostContentAndPostTitleAndPostType(String title, String context, String postType, Pageable pageable);

    /**
     * 根据用户id删除用户帖子
     * @param memberId
     */
    void deletePostEsBeansByPostMemberId(Integer memberId);
}
