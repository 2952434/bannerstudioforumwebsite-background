package studio.banner.forumwebsite.controller.frontdesk;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.ICommentService;
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
@Api(tags = "前台帖子回复接口", value = "ReplyFrontDeskController")
@RequestMapping("/frontDesk")
public class ReplyFrontDeskController {
    /**
     * 日志 打印信息
     */
    private static final Logger logger = LoggerFactory.getLogger(ReplyFrontDeskController.class);
    @Autowired
    protected IReplyService iReplyService;
    @Autowired
    protected ICommentService iCommentService;

    /**
     * 回复增加
     *
     * @param replyBean
     * @param bindingResult
     * @return RespBean
     */

    @PostMapping("/replyFrontDesk/insertReply")
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

        /**
         * 将@Valid鉴权的错误信息返给前端
         */

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

    /**
     * 回复删除
     *
     * @param replyId
     * @return RespBean
     */

    @DeleteMapping("/replyFrontDesk/deleteReply")
    @ApiOperation(value = "回复删除", notes = "回复需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean deleteComment(Integer replyId,Integer memberId) {

        return iReplyService.deleteReply(replyId,memberId);

    }


    /**
     * 根据评论id删除该评论下全部回复
     *
     * @param commentId
     * @return RespBean
     */
    @DeleteMapping("/replyFrontDesk/deleteAllReplyByCommentId")
    @ApiOperation(value = "根据评论id删除该评论下全部回复", notes = "评论需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean deleteAllCommentByCommentId(Integer commentId) {
        return iReplyService.deleteAllReplyByCommentId(commentId);

    }



    /**
     * 根据评论id查询该评论下全部回复
     *
     * @param commentId
     * @return RespBean
     */
    @GetMapping("/replyFrontDesk/selectAllReplyByCommentId")
    @ApiOperation(value = "根据评论id查找该评论下全部回复", notes = "评论需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectAllReplyByCommentId(Integer commentId,Integer page) {

        return iReplyService.selectAllReplyByCommentId(commentId,page);

    }


}
