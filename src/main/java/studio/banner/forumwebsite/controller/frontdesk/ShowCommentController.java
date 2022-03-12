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
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IReplyService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2022/3/12 10:46
 * @role:
 */
@RestController
@Api(tags = "前台评论展示接口", value = "ShowCommentController")
@RequestMapping("/frontDesk")
public class ShowCommentController {

    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IReplyService iReplyService;


    @GetMapping("/selectCommentInformation")
    @ApiOperation(value = "根据用户id查询评论信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectCommentInformation(Integer userId, Integer page){
        List<CommentBean> commentBeans = iCommentService.selectCommentByMemberId(userId, page);
        List<ReplyBean> replyBeans = iReplyService.selectReplyInformationById(userId, page);
        List<ShowCommentBean> list = new ArrayList<>();
        for (CommentBean commentBean : commentBeans) {
            list.add(new ShowCommentBean(commentBean));
        }
        for (ReplyBean replyBean : replyBeans) {
            list.add(new ShowCommentBean(replyBean));
        }
        Collections.sort(list);
        if (list.size()==0){
            return RespBean.error("无点赞信息");
        }
        return RespBean.ok("查询成功",list);
    }

    @PostMapping("/deleteCommentInformation")
    @ApiOperation(value = "根据评论id取消评论信息显示", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "judge",
                    value = "0删除评论，1删除回复", required = true, dataTypeClass = Integer.class)
    })
    public RespBean deleteCommentInformation(Integer commentId,Integer judge){
        if (judge==0){
            if (!iCommentService.deleteCommentInformationById(commentId)) {
                return RespBean.error("删除失败");
            }
        }else if (judge==1){
            if (!iReplyService.deleteReplyInformationById(commentId)) {
                return RespBean.error("删除失败");
            }
        }else {
            return RespBean.error("删除失败,无参数");
        }
        return RespBean.ok("删除成功");
    }


    @PostMapping("/deleteCommentAllInformation")
    @ApiOperation(value = "通过用户id删除评论信息全部展示", httpMethod = "POST")
    @ApiImplicitParam(paramType = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteCommentAllInformation(Integer userId) {
        iCommentService.deleteAllCommentByMemberId(userId);
        iReplyService.deleteAllReplyByMemberId(userId);
        return RespBean.ok("删除成功");
    }
}
