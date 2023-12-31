package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.ReplyBean;
import studio.banner.forumwebsite.bean.ReplyLikeBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IReplyLikeService;
import studio.banner.forumwebsite.service.IReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/17/22:15
 * @Description: 回复接口
 */
@RestController
@Api(tags = "前台帖子回复接口", value = "ReplyController")
@RequestMapping("/frontDesk")
public class ReplyController {

    private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
    @Autowired
    private IReplyService iReplyService;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IReplyLikeService iReplyLikeService;


    @PostMapping("/insertReply")
    @ApiOperation(value = "回复增加", notes = "回复内容不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "replyMemberId",
                    value = "回复发表者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "replyCommentMemberId",
                    value = "被回复者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "replyLikeNumber",
                    value = "回复点赞数量", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "replyTime",
                    value = "回复时间", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "replyContent",
                    value = "回复内容", required = true, dataTypeClass = String.class)
    })
    public RespBean insertComment(@Valid ReplyBean replyBean, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = new HashMap<>(999);
            List<FieldError> errors = bindingResult.getFieldErrors();
            logger.error("回复失败！");
            for (FieldError error : errors) {
                logger.error("错误的字段名：" + error.getField());
                logger.error("错误信息：" + error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
            return RespBean.error(map);
        }
        if (iCommentService.selectComment(replyBean.getCommentId()) != null) {
            String judge = "^.{2,100}$";

            if (replyBean.getReplyContent().matches(judge)) {
                iReplyService.insertReply(replyBean);
                return RespBean.ok("回复成功");
            }
            return RespBean.error("回复失败，回复内容不符合标准");
        }
        return RespBean.error("回复失败，未找到改评论");
    }


    @DeleteMapping("/deleteReply/{replyId}/{memberId}")
    @ApiOperation(value = "回复删除", notes = "回复需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean deleteComment(@PathVariable Integer replyId,@PathVariable Integer memberId) {
        return iReplyService.deleteReply(replyId,memberId);
    }


    @GetMapping("/replyFrontDesk/selectAllReplyByCommentId/{commentId}/{page}")
    @ApiOperation(value = "根据评论id查找该评论下全部回复", notes = "评论需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectAllReplyByCommentId(@PathVariable Integer commentId,@PathVariable Integer page) {
        return iReplyService.selectAllReplyByCommentId(commentId,page);
    }


    @GetMapping("/judgeReplyLike")
    @ApiOperation(value = "根据用户id和回复id判断是否具有点赞关系", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = true, dataTypeClass = Integer.class)
    })
    public boolean judgeReplyLike(Integer userId, Integer replyId) {
        return iReplyLikeService.judgeReplyLike(userId, replyId);
    }
    @PostMapping("/insertReplyLike")
    @ApiOperation(value = "增加点赞",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "likeId",
                    value = "点赞主键id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "likeUserId",
                    value = "点赞用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "beLikeUserId",
                    value = "被点赞用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "replyContent",
                    value = "回复内容", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "likeTime",
                    value = "点赞时间", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "show",
                    value = "是否展示0：展示1：不展示", required = true, dataTypeClass = Integer.class)
    })
    public RespBean insertReplyLike(ReplyLikeBean replyLikeBean) {
        return iReplyLikeService.insertReplyLike(replyLikeBean);
    }

    @DeleteMapping("/deleteReplyLike")
    @ApiOperation(value = "根据用户id，回复id，回复用户id取消点赞",httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "likeUserId",
                    value = "点赞用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "replyUserId",
                    value = "回复用户id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean deleteReplyLike(Integer userId, Integer replyId,Integer replyUserId) {
        return iReplyLikeService.deleteReplyLike(userId, replyId,replyUserId);
    }

}
