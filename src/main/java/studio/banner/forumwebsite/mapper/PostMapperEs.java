package studio.banner.forumwebsite.mapper;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import studio.banner.forumwebsite.bean.PostBeanEs;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/3 21:29
 * @role: es帖子映射
 */
@Repository
public interface PostMapperEs extends ElasticsearchRepository<PostBeanEs, Integer> {
    /**
     * 根据标题或内容查询（含分页）
     *
     * @param title    帖子标题
     * @param context  帖子内容
     * @param pageable 页数
     * @return List<PostBeanEs>
     */
    List<PostBeanEs> findByTitleOrContext(String title, String context, Pageable pageable);

}
