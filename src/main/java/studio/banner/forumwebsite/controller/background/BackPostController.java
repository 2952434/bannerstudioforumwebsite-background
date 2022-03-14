package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IFixedInformationService;
import studio.banner.forumwebsite.service.IPostService;
import studio.banner.forumwebsite.service.IPostTypeService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/10/21:30
 * Despriction 帖子操作接口
 */

@RestController
@Api(tags = "后台帖子接口", value = "BackPostController")
@RequestMapping("/backGround")
public class BackPostController {
    private static final Logger logger = LoggerFactory.getLogger(BackPostController.class);
    @Autowired
    private IPostService iPostService;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IPostTypeService iPostTypeService;
    @Autowired
    private IFixedInformationService iFixedInformationService;


    @GetMapping("/selectPostAllType")
    @ApiOperation(value = "查询所有帖子类型",httpMethod = "GET")
    public RespBean selectPostAllType() {
        return iPostTypeService.selectPostAllType();
    }

    @PostMapping("/insertPostType")
    @ApiOperation(value = "增加帖子类型",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "帖子类型主键id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postType",
                    value = "帖子类型名", required = true, dataTypeClass = String.class)
    })
    public RespBean insertPostType(PostTypeBean postTypeBean) {
        return iPostTypeService.insertPostType(postTypeBean);
    }

    @DeleteMapping("/deletePostTypeById")
    @ApiOperation(value = "根据id删除帖子类型",httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "typeId",
            value = "帖子类型主键id", required = true, dataTypeClass = Integer.class)
    public RespBean deletePostTypeById(Integer typeId) {
        return iPostTypeService.deletePostTypeById(typeId);
    }


    @DeleteMapping("/deletePostById")
    @ApiOperation(value = "根据帖子id删除帖子",httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "postId",
            value = "帖子主键id", required = true, dataTypeClass = Integer.class)
    public RespBean deletePostById(Integer postId) {
        return iPostService.deletePostById(postId);
    }

    @GetMapping("/selectPost")
    @ApiOperation(value = "帖子查找", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectPost(Integer postId) {
        PostBean postBean = iPostService.selectPost(postId);
        if (postBean!=null){
            return RespBean.ok("查找成功", postBean);
        }
        return RespBean.error("查找失败，未查询到该帖子");
    }

    @GetMapping("/postBackGround/selectAllPostByDescById")
    @ApiOperation(value = "根据时间倒序查询某用户所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectAllPostByDescById(Integer postMemberId) {
        if (iPostService.selectAllPostByDescById(postMemberId) != null) {
            List<PostBean> list = iPostService.selectAllPostByDescById(postMemberId);
            return RespBean.ok("查找成功", list);
        }
        return RespBean.error("查找失败，未查询到该用户或该用户无帖子");
    }


    @GetMapping("/postBackGround/selectAllPostByAscById")
    @ApiOperation(value = "根据时间正序查询某用户所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectAllPostByAscById(Integer postMemberId) {
        if (iPostService.selectAllPostByAscById(postMemberId) != null) {
            List<PostBean> list = iPostService.selectAllPostByAscById(postMemberId);
            return RespBean.ok("查找成功", list);
        }
        return RespBean.error("查找失败，未查询到该用户或该用户无帖子");
    }


}