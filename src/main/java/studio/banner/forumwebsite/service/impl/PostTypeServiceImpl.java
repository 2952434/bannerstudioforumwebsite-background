package studio.banner.forumwebsite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.banner.forumwebsite.bean.PostTypeBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.mapper.PostTypeMapper;
import studio.banner.forumwebsite.service.IPostTypeService;

import javax.management.Query;
import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/11 14:42
 * @role:
 */
@Service
public class PostTypeServiceImpl implements IPostTypeService {

    @Autowired
    private PostTypeMapper postTypeMapper;

    @Override
    public boolean selectPostTypeByName(String name) {
        QueryWrapper<PostTypeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_type",name);
        return postTypeMapper.selectOne(queryWrapper) == null;
    }

    @Override
    public RespBean insertPostType(PostTypeBean postTypeBean) {
        if (selectPostTypeByName(postTypeBean.getPostType())){
            postTypeMapper.insert(postTypeBean);
            return RespBean.ok("添加成功");
        }
        return RespBean.error("该类型以存在添加失败");
    }

    @Override
    public RespBean deletePostTypeById(Integer typeId) {
        if (postTypeMapper.deleteById(typeId)==1) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败无该类型");
    }

    @Override
    public RespBean selectPostAllType() {
        List<PostTypeBean> postTypeBeans = postTypeMapper.selectList(null);
        if (postTypeBeans.size()!=0){
            return RespBean.ok("查询成功",postTypeBeans);
        }
        return RespBean.error("无帖子类型");
    }


}
