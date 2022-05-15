package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.service.ICommentLikeService;
import studio.banner.forumwebsite.service.IPostLikeService;
import studio.banner.forumwebsite.service.IReplyLikeService;

import java.util.*;

/**
 * @Author: Ljx
 * @Date: 2022/3/11 22:29
 * @role:
 */
@RestController
@Api(tags = "前台点赞展示接口", value = "ShowLikeController")
@RequestMapping("/frontDesk")
public class ShowLikeController {

    @Autowired
    private IPostLikeService iPostLikeService;

    @GetMapping("/selectUserLikeInformation/{userId}/{page}")
    @ApiOperation(value = "根据用户id查询点赞信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectUserLikeInformation(@PathVariable Integer userId,@PathVariable Integer page){
        List<Map<String, String>> postLikeBeans = iPostLikeService.selectPostLikeByUserId(userId, page);
        return RespBean.ok("查询成功",postLikeBeans);
    }

    @DeleteMapping("/deletePostLikeInformation/{likeId}")
    @ApiOperation(value = "根据点赞id取消帖子点赞信息显示", httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "likeId",
                    value = "点赞id", required = true, dataTypeClass = Integer.class)
    public RespBean deletePostLikeInformation(@PathVariable Integer likeId){
        if (!iPostLikeService.deletePostLikeInformation(likeId)) {
            return RespBean.error("删除失败");
        }
        return RespBean.ok("删除成功");
    }


    @DeleteMapping("/deletePostLikeAllInformation/{userId}")
    @ApiOperation(value = "通过用户id取消点赞信息全部展示", httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean deletePostLikeAllInformation(@PathVariable Integer userId) {
        if (iPostLikeService.deletePostLikeAllInformation(userId)) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
