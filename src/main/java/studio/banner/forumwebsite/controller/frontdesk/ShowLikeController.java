package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private ICommentLikeService iCommentLikeService;
    @Autowired
    private IPostLikeService iPostLikeService;
    @Autowired
    private IReplyLikeService iReplyLikeService;

    @GetMapping("/selectUserLikeInformation")
    @ApiOperation(value = "根据用户id查询点赞信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectUserLikeInformation(Integer userId,Integer page){
        List<CommentLikeBean> commentLikeBeans = iCommentLikeService.selectCommentLikeByBeLikeUserId(userId, page);
        List<PostLikeBean> postLikeBeans = iPostLikeService.selectPostLikeByUserId(userId, page);
        List<ReplyLikeBean> replyLikeBeans = iReplyLikeService.selectReplyLikeByBeLikeUserId(userId, page);
        List<ShowLikeBean> list = new ArrayList<>();
        for (CommentLikeBean commentLikeBean : commentLikeBeans) {
            list.add(new ShowLikeBean(commentLikeBean));
        }
        for (PostLikeBean postLikeBean : postLikeBeans) {
            list.add(new ShowLikeBean(postLikeBean));
        }
        for (ReplyLikeBean replyLikeBean : replyLikeBeans) {
            list.add(new ShowLikeBean(replyLikeBean));
        }
        Collections.sort(list);
        if (list.size()==0){
            return RespBean.error("无点赞信息");
        }
        return RespBean.ok("查询成功",list);
    }

    @PostMapping("/deleteLikeInformation")
    @ApiOperation(value = "根据点赞id取消点赞信息显示", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "likeId",
                    value = "点赞id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "judge",
                    value = "0删除帖子点赞，1删除评论点赞，2删除回复点赞", required = true, dataTypeClass = Integer.class)
    })
    public RespBean deleteLikeInformation(Integer likeId,Integer judge){
        if (judge==0){
            if (!iPostLikeService.deletePostLikeInformation(likeId)) {
                return RespBean.error("删除失败");
            }
        }else if (judge==1){
            if (!iCommentLikeService.deleteCommentLikeByLikeId(likeId)) {
                return RespBean.error("删除失败");
            }
        }else if (judge==2){
            if (!iReplyLikeService.deleteReplyLikeByLikeId(likeId)) {
                return RespBean.error("删除失败");
            }
        }else {
            return RespBean.error("删除失败,无参数");
        }
        return RespBean.ok("删除成功");
    }


    @PostMapping("/deleteLikeAllInformation")
    @ApiOperation(value = "通过用户id取消点赞信息全部展示", httpMethod = "POST")
    @ApiImplicitParam(paramType = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteLikeAllInformation(Integer userId) {
        iPostLikeService.deletePostLikeAllInformation(userId);
        iCommentLikeService.deleteAllCommentLikeByBeLikeUserId(userId);
        iReplyLikeService.deleteAllReplyLikeByBeLikeUserId(userId);
        return RespBean.ok("删除成功");
    }

}
