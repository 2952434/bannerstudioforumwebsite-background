package studio.banner.forumwebsite.service.impl;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostEsBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.mapper.PostEsMapper;
import studio.banner.forumwebsite.mapper.PostMapper;
import studio.banner.forumwebsite.service.IPostEsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2022/3/12 16:41
 * @role:
 */
@Service
public class PostEsServiceImpl implements IPostEsService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostEsMapper postEsMapper;

    /**
     * 每两分钟更新一次es中的数据
     */
    @Override
    @Scheduled(cron = "0 */2 * * * ?")
    public void updateEsPost() {
        List<Map<String, String>> maps = postMapper.selectListPost();
        for (Map<String, String> map : maps) {
            String post_id = String.valueOf(map.get("post_id"));
//            System.out.println(post_id);
            postEsMapper.save(new PostEsBean(map));
        }
    }

    @Override
    public RespBean findAllWithPage(Integer page) {
        PageRequest pageable = PageRequest.of(page-1, 10);
        Page<PostEsBean> all = postEsMapper.findAll(pageable);
        if (all.getContent().size()==0){
            return RespBean.error("未查询到相关内容",all);
        }
        return RespBean.ok("查询成功",all);
    }

    @Override
    public RespBean findByPostContentAndPostTitleAndPostType(String condition, Integer page) {
        //根据一个值查询多个字段  并高亮显示  这里的查询是取并集，即多个字段只需要有一个字段满足即可
        //需要查询的字段
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("postTitle",condition))
                .should(QueryBuilders.matchQuery("postContent",condition))
                .should(QueryBuilders.matchQuery("postType",condition))
                .should(QueryBuilders.matchQuery("memberName",condition));
        Pageable pageable = PageRequest.of(page-1, 10);
        //构建高亮查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .withHighlightFields(
                        new HighlightBuilder.Field("postTitle"),
                        new HighlightBuilder.Field("postContent"),
                        new HighlightBuilder.Field("memberName"),
                        new HighlightBuilder.Field("postType"))
                .withHighlightBuilder(new HighlightBuilder().preTags("<span style='color:red'>").postTags("</span>"))
                .build();
        //查询
        SearchHits<PostEsBean> search = elasticsearchRestTemplate.search(searchQuery, PostEsBean.class);
        //得到查询返回的内容
        List<org.springframework.data.elasticsearch.core.SearchHit<PostEsBean>> searchHits = search.getSearchHits();
        //设置一个最后需要返回的实体类集合
        List<PostEsBean> postEsBeans = new ArrayList<>();
        //遍历返回的内容进行处理
        for(SearchHit<PostEsBean> searchHit:searchHits){
            //高亮的内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            //将高亮的内容填充到content中
            searchHit.getContent().setPostTitle(highlightFields.get("postTitle")==null ? searchHit.getContent().getPostTitle():highlightFields.get("postTitle").get(0));
            searchHit.getContent().setPostContent(highlightFields.get("postContent")==null ? searchHit.getContent().getPostContent():highlightFields.get("postContent").get(0));
            searchHit.getContent().setPostType(highlightFields.get("postType")==null ? searchHit.getContent().getPostType():highlightFields.get("postType").get(0));
            searchHit.getContent().setMemberName(highlightFields.get("memberName")==null ? searchHit.getContent().getMemberName():highlightFields.get("memberName").get(0));
            //放到实体类中
            postEsBeans.add(searchHit.getContent());
        }
        if (postEsBeans.size()==0){
            return RespBean.error("未查询到相关内容",postEsBeans);
        }
        return RespBean.ok("查询成功",postEsBeans);
    }

    @Override
    public void deleteEsPostById(Integer postId) {
        postEsMapper.deleteById(postId);
    }

    @Override
    public void deleteEsPostByMemberId(Integer memberId) {
        postEsMapper.deletePostEsBeansByPostMemberId(memberId);
    }
}
