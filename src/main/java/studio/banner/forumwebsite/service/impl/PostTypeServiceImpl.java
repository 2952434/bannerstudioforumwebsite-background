package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.PostContactBean;
import studio.banner.forumwebsite.bean.PostTypeBean;
import studio.banner.forumwebsite.mapper.PostContactMapper;
import studio.banner.forumwebsite.mapper.PostMapper;
import studio.banner.forumwebsite.mapper.PostTypeMapper;
import studio.banner.forumwebsite.service.IPostTypeService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 15:10
 * @role: 帖子类型操作实现
 */
@Service
public class PostTypeServiceImpl implements IPostTypeService {

    @Autowired
    private PostContactMapper postContactMapper;

    @Autowired
    private PostTypeMapper postTypeMapper;

    @Autowired
    private PostMapper postMapper;

    /**
     * 通过帖子id查询帖子所属类型
     *
     * @param postId 帖子id
     * @return String[]
     */
    @Override
    public String[] selectPostTypeById(Integer postId) {
        QueryWrapper<PostContactBean> postContactBeanQueryWrapper = new QueryWrapper<>();
        postContactBeanQueryWrapper.eq("post_id", postId);
        List<PostContactBean> postContactBeans = postContactMapper.selectList(postContactBeanQueryWrapper);
        if (postContactBeans.size() != 0) {
            int i = 0;
            String[] arr = new String[postContactBeans.size()];
            for (PostContactBean postContactBean : postContactBeans) {
                Integer postTypeId = postContactBean.getPostTypeId();
                QueryWrapper<PostTypeBean> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", postTypeId);
                PostTypeBean postTypeBean = postTypeMapper.selectOne(queryWrapper);
                arr[i] = postTypeBean.getPostType();
                i++;
            }
            return arr;
        }
        return null;
    }

    /**
     * 增加帖子类型
     *
     * @param postType 帖子类型
     * @return boolean
     */
    @Override
    public boolean insertPostType(PostTypeBean postType) {
        QueryWrapper<PostTypeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_type", postType.getPostType());
        PostTypeBean postTypeBean = postTypeMapper.selectOne(queryWrapper);
        if (postTypeBean == null) {
            int insert = postTypeMapper.insert(postType);
            return insert == 1;
        } else {
            return false;
        }
    }

    /**
     * 查询所有帖子类型
     *
     * @return List<PostTypeBean>
     */
    @Override
    public List<PostTypeBean> selectAllPostType() {
        return postTypeMapper.selectList(null);
    }

    /**
     * 删除帖子类型
     *
     * @param postType 帖子类型
     * @return boolean
     */
    @Override
    public boolean deletePostType(String postType) {
        QueryWrapper<PostTypeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_type", postType);
        PostTypeBean postTypeBean = postTypeMapper.selectOne(queryWrapper);
        UpdateWrapper<PostContactBean> updateWrapper1 = new UpdateWrapper<>();
        if (postTypeBean == null) {
            return false;
        } else {
            updateWrapper1.eq("post_type_id", postTypeBean.getId());
            postContactMapper.delete(updateWrapper1);
            UpdateWrapper<PostTypeBean> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("post_type", postType);
            int delete = postTypeMapper.delete(updateWrapper);
            return delete == 1;
        }
    }

    /**
     * 通过类型分页查询该类型的帖子
     *
     * @param postType 帖子类型
     * @return List<PostBean>
     */
    @Override
    public List<PostBean> selectPostByType(String postType) {
        QueryWrapper<PostTypeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_type", postType);
        PostTypeBean postTypeBean = postTypeMapper.selectOne(queryWrapper);
        if (postTypeBean == null) {
            return null;
        } else {
            QueryWrapper<PostContactBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("post_type_id", postTypeBean.getId());
            List<PostContactBean> postContactBeans = postContactMapper.selectList(queryWrapper1);
            if (postContactBeans.size() == 0) {
                return null;
            } else {
                List<PostBean> list = new ArrayList<>();
                for (PostContactBean postContactBean : postContactBeans) {
                    PostBean postBean = postMapper.selectById(postContactBean.getPostId());
                    list.add(postBean);
                }
                return list;
            }
        }
    }

    /**
     * 通过类型分页查询该类型的帖子根据浏览量逆向排序
     *
     * @param postType 帖子类型
     * @return List<PostBean>
     */
    @Override
    public List<PostBean> selectPostDescByType(String postType) {
        QueryWrapper<PostTypeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_type", postType);
        PostTypeBean postTypeBean = postTypeMapper.selectOne(queryWrapper);
        if (postTypeBean == null) {
            return null;
        } else {
            QueryWrapper<PostContactBean> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("post_type_id", postTypeBean.getId());
            List<PostContactBean> postContactBeans = postContactMapper.selectList(queryWrapper1);
            if (postContactBeans.size() == 0) {
                return null;
            } else {
                List<PostBean> list = new ArrayList<>();
                for (PostContactBean postContactBean : postContactBeans) {
                    PostBean postBean = postMapper.selectById(postContactBean.getPostId());
                    list.add(postBean);
                }
                Comparator<PostBean> comparator = new Comparator<PostBean>() {
                    @Override
                    public int compare(PostBean s1, PostBean s2) {
                        return s2.getPostPageView() - s1.getPostPageView();
                    }
                };
                list.sort(comparator);
                if (list.size() > 10) {
                    List<PostBean> list1 = list.subList(0, 10);
                    return list1;
                }
                return list;
            }
        }
    }
}
