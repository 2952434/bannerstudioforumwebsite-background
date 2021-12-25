package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.PostTypeBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IPostTypeService;
import studio.banner.forumwebsite.utils.JsonUtils;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 15:42
 * @role:
 */
@RestController
@Api(tags = "后台帖子类型接口", value = "PostTypeBackGroundController")
public class PostTypeBackGroundController {

    @Autowired
    private IPostTypeService iPostTypeService;

    @GetMapping("/postTypeBackGroundController/selectPostTypeById")
    @ApiOperation(value = "通过帖子id查询类型", notes = "帖子id需存在", httpMethod = "GET")
    @ApiImplicitParam(paramType = "query", name = "postId",
            value = "帖子Id", required = true, dataTypeClass = Integer.class)
    public RespBean selectPostTypeById(Integer postId) {
        String[] arr = iPostTypeService.selectPostTypeById(postId);
        if (arr.length != 0) {
            return RespBean.ok("查询成功", arr);
        } else {
            return RespBean.error("查询失败！！！");
        }
    }

    @PostMapping("/postTypeBackGroundController/insertPostType")
    @ApiOperation(value = "添加帖子类型", notes = "添加类型不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "帖子类型id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postType",
                    value = "帖子类型", required = true, dataTypeClass = String.class)
    })
    public RespBean insertPostType(PostTypeBean postType) {
        boolean insertPostType = iPostTypeService.insertPostType(postType);
        if (insertPostType) {
            return RespBean.ok("添加类型成功！！！");
        } else {
            return RespBean.error("该类型已存在，添加类型失败！！！");
        }
    }

    @GetMapping("/postTypeBackGroundController/selectAllPostType")
    @ApiOperation(value = "查询所有帖子类型", httpMethod = "GET")
    public RespBean selectAllPostType() {
        List<PostTypeBean> list = iPostTypeService.selectAllPostType();
        if (list.size() != 0) {
            return RespBean.ok("查询成功", list);
        } else {
            return RespBean.error("查询失败！！！");
        }
    }

    @DeleteMapping("/postTypeBackGroundController/deletePostType")
    @ApiOperation(value = "删除帖子类型", notes = "帖子类型不能为空", httpMethod = "DELETE")
    @ApiImplicitParam(type = "query", name = "postType",
            value = "帖子类型不能为空", required = true, dataTypeClass = String.class)
    public RespBean deletePostType(String postType) {
        boolean deletePostType = iPostTypeService.deletePostType(postType);
        if (deletePostType) {
            return RespBean.ok("删除成功！！！");
        } else {
            return RespBean.error("该类型已不存在，删除失败！！！");
        }
    }
}
