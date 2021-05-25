package studio.banner.forumwebsite.controller.background;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.org.apache.regexp.internal.RE;
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
@Api(tags = "回复接口",value = "ReplyController")
public class ReplyController {
    /**
     * 日志 打印信息
     */
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    protected IReplyService iReplyService;
    @Autowired
    protected ICommentService iCommentService;

    /**
     * 回复增加
     * @param replyBean
     * @param bindingResult
     * @return RespBean
     */

    @PostMapping("/insertReply")
    @ApiOperation(value = "回复增加", notes = "回复内容不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "replyMemberId",
                    value = "回复发表者id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "replyCommentMemberId",
                    value = "被回复者id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "replyLikeNumber",
                    value = "回复点赞数量", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "replyTime",
                    value = "回复时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "replyContent",
                    value = "回复内容", required = true, dataType = "String")
    }
    )
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
            iReplyService.insertReply(replyBean);
            return RespBean.ok("回复成功");
        }
        return RespBean.error("回复失败，未找到改评论");
    }

    /**
     * 回复删除
     * @param replyId
     * @return RespBean
     */

    @DeleteMapping("/deleteReply")
    @ApiOperation(value = "回复删除", notes = "回复需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = true, dataType = "int"),
    }
    )
    public RespBean deleteComment(Integer replyId) {
        if (iReplyService.selectReply(replyId) != null){
            iReplyService.deleteReply(replyId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败，未找到该评论");
    }

    /**
     * 根据用户id删除该用户下全部回复
     * @param replyMemberId
     * @return RespBean
     */
    @DeleteMapping("/deleteAllReplyByMemberId")
    @ApiOperation(value = "根据用户id删除该用户全部回复", notes = "用户需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyMemberId",
                    value = "用户id", required = true, dataType = "int"),
    }
    )
    public RespBean deleteAllCommentByMemberId(Integer replyMemberId) {
        if (iReplyService.selectAllReplyByMemberId(replyMemberId, 1).getRecords().size() != 0){
            iReplyService.deleteAllReplyByMemberId(replyMemberId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败，未找到该用户或该用户无评论");
    }

    /**
     * 根据评论id删除该评论下全部回复
     * @param commentId
     * @return RespBean
     */
    @DeleteMapping("/deleteAllReplyByCommentId")
    @ApiOperation(value = "根据评论id删除该评论下全部回复", notes = "评论需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataType = "int"),
    }
    )
    public RespBean deleteAllCommentByCommentId(Integer commentId) {
        if (iReplyService.selectAllReplyByCommentId(commentId ,1).getRecords().size() != 0){
            iReplyService.deleteAllReplyByCommentId(commentId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败，未找到该评论或改评论下无评论可删除");
    }

    /**
     * 回复内容修改
     * @param replyId
     * @param newContent
     * @return RespBean
     */
    @PutMapping("/updateReplyContent")
    @ApiOperation(value = "回复内容修改", notes = "回复需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "newContent",
                    value = "回复新内容", required = true, dataType = "String")
    }
    )
    public RespBean updateCommentContent(Integer replyId, String newContent) {
        if (iReplyService.selectReply(replyId) != null){
            iReplyService.updateReplyContent(replyId,newContent);
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败，未找到该回复");
    }

    /**
     * 回复点赞量修改
     * @param replyId
     * @return RespBean
     */
    @PutMapping("/updateReplyLikeNumber")
    @ApiOperation(value = "回复点赞量修改", notes = "回复需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = true, dataType = "int"),
    }
    )
    public RespBean updateCommentLikeNumber(Integer replyId) {
        if (iReplyService.selectReply(replyId) != null){
            iReplyService.updateReplyLikeNumber(replyId);
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败，未找到该回复");
    }

    /**
     * 根据评论id查询该评论下全部回复
     * @param commentId
     * @return RespBean
     */
    @GetMapping("/selectAllReplyByCommentId")
    @ApiOperation(value = "根据评论id查找该评论下全部回复", notes = "评论需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataType = "int")
    }
    )
    public RespBean selectAllReplyByCommentId(Integer commentId, int page) {
            IPage<ReplyBean> iPage = iReplyService.selectAllReplyByCommentId(commentId, page);
            List<ReplyBean>list = iPage.getRecords();
            if (list.size() != 0){
            return RespBean.ok("查询成功",list);
        }
        return RespBean.error("查询失败，未找到该评论或该评论下无此页");
    }

    /**
     * 根据用户id查询该用户下全部回复
     * @param replyMemberId
     * @return RespBean
     */
    @GetMapping("/selectAllReplyByMemberId")
    @ApiOperation(value = "根据用户id查找该用户下全部回复", notes = "用户需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyMemberId",
                    value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataType = "int")
    }
    )
    public RespBean selectAllReplyByMemberId(Integer replyMemberId ,int page) {
            IPage<ReplyBean> iPage = iReplyService.selectAllReplyByMemberId(replyMemberId, page);
            List<ReplyBean>list = iPage.getRecords();
            if (list.size() != 0){

            return RespBean.ok("查询成功",list);
        }
        return RespBean.error("查询失败，未找到该用户或该用户评论下无此页");
    }

    /**
     * 根据回复id查询该回复
     * @param replyId
     * @return RespBean
     */
    @GetMapping("/selectReply")
    @ApiOperation(value = "根据回复id查询回复", notes = "回复需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "replyId",
                    value = "回复id", required = true, dataType = "int")
    }
    )
    public RespBean selectReply(Integer replyId) {
        if (iReplyService.selectReply(replyId) != null){
            ReplyBean replyBean = iReplyService.selectReply(replyId);
            return RespBean.ok("查询成功",replyBean);
        }
        return RespBean.error("查询失败，未找到该回复");
    }
    }
