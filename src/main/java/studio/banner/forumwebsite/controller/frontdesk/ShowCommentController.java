package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IReplyService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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


    @GetMapping("/selectCommentInformation/{memberId}/{page}")
    @ApiOperation(value = "根据用户id查询评论信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectCommentInformation(@PathVariable Integer memberId,@PathVariable Integer page){
        List<Map<String, String>> commentBeans = iCommentService.selectCommentByMemberId(memberId, page);

        return RespBean.ok("查询成功",commentBeans);
    }

    @GetMapping("/selectReplyInformationById/{memberId}/{page}")
    @ApiOperation(value = "根据用户id查询回复信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectReplyInformationById(@PathVariable Integer memberId,@PathVariable Integer page){
        List<Map<String, String>> replyBeans = iReplyService.selectReplyInformationById(memberId, page);
        return RespBean.ok("查询成功",replyBeans);
    }

    @DeleteMapping("/deleteCommentInformation/{commentId}/{judge}")
    @ApiOperation(value = "根据评论id取消评论信息显示", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "judge",
                    value = "0删除评论，1删除回复", required = true, dataTypeClass = Integer.class)
    })
    public RespBean deleteCommentInformation(@PathVariable Integer commentId,@PathVariable Integer judge){
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


    @DeleteMapping("/deleteCommentAllInformation/{userId}")
    @ApiOperation(value = "通过用户id删除评论信息全部展示", httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteCommentAllInformation(@PathVariable Integer userId) {
        if (iCommentService.deleteAllCommentInformationById(userId)) {
            return RespBean.ok("删除成功");
        }else {
            return RespBean.error("删除失败");
        }

    }

    @DeleteMapping("/deleteAllReplyInformationById/{userId}")
    @ApiOperation(value = "通过用户id删除回复信息全部展示", httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteAllReplyInformationById(@PathVariable Integer userId) {
        boolean b = iReplyService.deleteAllReplyInformationById(userId);
        if (b){
            return RespBean.ok("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }

    @GetMapping("/selectAllReplyNumByCommentId/{commentId}")
    @ApiOperation(value = "根据评论id查询该评论下的回复数量",httpMethod = "GET")
    @ApiImplicitParam(dataType = "query",name = "commentId",
            value = "评论id",required = true,dataTypeClass = Integer.class)
    public Integer selectAllReplyNumByCommentId(@PathVariable Integer commentId) {
        return iCommentService.selectAllCommentNumByPostId(commentId);
    }
}
