package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.CommentLikeBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.ICommentLikeService;

/**
 * @Author: Ljx
 * @Date: 2022/3/11 15:53
 * @role:
 */
@RestController
@Api(tags = "前台评论点赞接口", value = "CommentLikeController")
@RequestMapping("/frontDesk")
public class CommentLikeController {

    @Autowired
    private ICommentLikeService iCommentLikeService;

    @GetMapping("/judgeCommentLike")
    @ApiOperation(value = "根据用户id和评论id查询该用户是否点赞该评论", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean judgeCommentLike(Integer userId, Integer commentId) {
        if (iCommentLikeService.judgeCommentLike(userId, commentId)) {
            return RespBean.ok("该用户未点赞该评论");
        }
        return RespBean.error("该用户以点赞该评论");
    }

    @PostMapping("/insertCommentLike")
    @ApiOperation(value = "增加点赞信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "likeId",
                    value = "点赞主键id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "likeUserId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean insertCommentLike(CommentLikeBean commentLikeBean) {

        return iCommentLikeService.insertCommentLike(commentLikeBean);

    }


    @DeleteMapping("/deleteCommentLike")
    @ApiOperation(value = "取消点赞", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "commentUserId",
                    value = "评论者id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean deleteCommentLike(Integer userId, Integer commentId,Integer commentUserId) {
        return iCommentLikeService.deleteCommentLike(userId, commentId,commentUserId);
    }

}
