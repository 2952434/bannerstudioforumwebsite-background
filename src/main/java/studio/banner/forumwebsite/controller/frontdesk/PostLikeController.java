package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.PostLikeBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IPostLikeService;

/**
 * @Author: Ljx
 * @Date: 2022/3/10 21:15
 * @role:
 */
@RestController
@Api(tags = "前台帖子点赞接口", value = "BackPostController")
@RequestMapping("/frontDesk")
public class PostLikeController {

    @Autowired
    private IPostLikeService iPostLikeService;

    @GetMapping("/judgePostLike/{postId}/{userId}")
    @ApiOperation(value = "根据帖子id和用户id判断是否点赞", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子的id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = false, dataTypeClass = Integer.class)
    })
    public RespBean judgePostLike(@PathVariable Integer postId,@PathVariable Integer userId) {
        Integer integer = iPostLikeService.selectPostLikeNum(postId);
        if (iPostLikeService.judgePostLike(postId, userId)) {
            return RespBean.ok(String.valueOf(integer),true);
        }
        return RespBean.ok(String.valueOf(integer),false);
    }

    @PostMapping("/insertPostLike")
    @ApiOperation(value = "帖子点赞增加", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "likeId",
                    value = "帖子点赞主键id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userLikeId",
                    value = "点赞者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "beUserLikeId",
                    value = "被点赞者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "likePostId",
                    value = "点赞帖子的id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "likePostTitle",
                    value = "点赞帖子的标题", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "likeTime",
                    value = "点赞时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "likeShow",
                    value = "是否展示0展示1不展示", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userName",
                    value = "点赞用户昵称", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "headUrl",
                    value = "用户头像", required = false, dataTypeClass = String.class)
    })
    public RespBean insertPostLike(PostLikeBean postLikeBean) {
        return iPostLikeService.insertPostLike(postLikeBean);
    }

    @DeleteMapping("/deletePostLikeById/{postId}/{userId}")
    @ApiOperation(value = "根据帖子id和用户id取消点赞", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子的id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean deletePostLikeById(@PathVariable Integer postId,@PathVariable Integer userId) {
        return iPostLikeService.deletePostLikeById(postId, userId);
    }

    @GetMapping("/selectPostLikeNumByUserId/{userId}")
    @ApiOperation(value = "根据用户id查询被点赞数量", httpMethod = "GET")
    @ApiImplicitParam(paramType = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectPostLikeNumByUserId(@PathVariable Integer userId){
        return RespBean.ok(iPostLikeService.selectPostLikeNumByUserId(userId));
    }



}
