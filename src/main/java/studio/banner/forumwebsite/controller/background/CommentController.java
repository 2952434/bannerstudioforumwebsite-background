package studio.banner.forumwebsite.controller.background;

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
import studio.banner.forumwebsite.bean.CommentBean;
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
 * @Date: 2021/05/15/21:23
 * @Description: 评论接口
 */
@RestController
@Api(tags = "评论接口",value = "CommentController")
public class CommentController {
    /**
     * 日志 打印信息
     */
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    protected ICommentService iCommentServicel;
    @Autowired
    protected IReplyService iReplyService;

    /**
     * 评论增加接口
     * @param commentBean
     * @param bindingResult
     * @return RespBean
     */
    @PostMapping("/insertComment")
    @ApiOperation(value = "评论增加", notes = "评论内容不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "commentPostId",
                    value = "帖子id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "commentMemberId",
                    value = "评论发表者id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "commentLikeNumber",
                    value = "评论点赞数量", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "commentContent",
                    value = "评论内容", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "commentTime",
                    value = "评论时间", required = true, dataType = "String")
    }
    )
    public RespBean insertComment(@Valid CommentBean commentBean, BindingResult bindingResult) {

        /**
         * 将@Valid鉴权的错误信息返给前端
         */

        if (bindingResult.hasErrors()) {
            Map<String, Object> map = new HashMap<>(999);
            List<FieldError> errors = bindingResult.getFieldErrors();
            logger.error("增加失败！");
            for (FieldError error : errors) {
                logger.error("错误的字段名：" + error.getField());
                logger.error("错误信息：" + error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
            return RespBean.error(map);
        }
       iCommentServicel.insertComment(commentBean);
        return RespBean.ok("评论成功");
    }

    /**
     * 根据帖子id删除该帖子下全部评论接口
     * @param commentPostId
     * @return RespBean
     */

    @DeleteMapping("/deleteAllCommentByPostId")
    @ApiOperation(value = "根据帖子id删除该帖子下全部评论", notes = "帖子需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentPostId",
                    value = "评帖子id", required = true, dataType = "int"),
    }
    )
    public RespBean deleteAllCommentByPostId(int commentPostId) {
        if (iCommentServicel.selectAllCommentByPostId(commentPostId) != null){
            List<CommentBean> list = iCommentServicel.selectAllCommentByPostId(commentPostId);
            for (int i = 0; i < list.size(); i++) {
                iReplyService.deleteAllReplyByCommentId(list.get(i).getCommentId());
            }
            iCommentServicel.deleteAllCommnetByPostId(commentPostId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败，未找到该帖子或该帖子下无评论");
    }

    /**
     * 根据用户id删除该用户下全部评论
     * @param commentMemberId
     * @return RespBean
     */
    @DeleteMapping("/deleteAllCommentByMemberId")
    @ApiOperation(value = "根据用户id删除该用户下全部评论", notes = "用户需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentMemberId",
                    value = "用户id", required = true, dataType = "int"),
    }
    )
    public RespBean deleteAllCommentByMemberId(int commentMemberId) {
        if (iCommentServicel.selectAllCommentByMemberId(commentMemberId) != null){
            List<CommentBean>list = iCommentServicel.selectAllCommentByMemberId(commentMemberId);
            for (int i = 0; i < list.size(); i++) {
                iReplyService.deleteAllReplyByCommentId(list.get(i).getCommentId());
            }
            iCommentServicel.deleteAllCommentByMemberId(commentMemberId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败，未找到该用户或该用户无评论");
    }

    /**
     * 评论删除
     * @param commentId
     * @return RespBean
     */
    @DeleteMapping("/deleteComment")
    @ApiOperation(value = "评论删除", notes = "评论需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataType = "int"),
    }
    )
    public RespBean deleteComment(int commentId) {
        if (iCommentServicel.selectComment(commentId) != null){
            iReplyService.deleteAllReplyByCommentId(commentId);
            iCommentServicel.deleteComment(commentId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败，未找到该评论");
    }

    /**
     * 评论内容修改
     * @param commentId
     * @param newContent
     * @return RespBean
     */

    @PutMapping("/updateCommentContent")
    @ApiOperation(value = "评论内容修改", notes = "评论需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "newContent",
                    value = "新评论内容", required = true, dataType = "String")

    }
    )
    public RespBean updateCommentContent(int commentId, String newContent) {
        if (iCommentServicel.selectComment(commentId) != null){
            iCommentServicel.updateCommentContent(commentId,newContent);
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败，未找到该评论");
    }

    /**
     * 评论点赞量修改
     * @param commentId
     * @return RespBean
     */

    @PutMapping("/updateCommentLikeNumber")
    @ApiOperation(value = "评论点赞量修改", notes = "评论需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataType = "int"),

    }
    )
    public RespBean updateCommentLikeNumber(int commentId) {
        if (iCommentServicel.selectComment(commentId) != null){
            iCommentServicel.updateCommentLikeNumber(commentId);
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败，未找到该评论");
    }

    /**
     * 根据帖子id查询该帖子下全部评论
     * @param commentPostId
     * @return RespBean
     */

    @GetMapping("/selectAllCommentByPostId")
    @ApiOperation(value = "根据帖子id查询该帖子下全部评论", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentPostId",
                    value = "帖子id", required = true, dataType = "int")

    }
    )
    public RespBean selectAllCommentByPostId(int commentPostId) {
        if (iCommentServicel.selectAllCommentByPostId(commentPostId) != null){
           List<CommentBean> list = iCommentServicel.selectAllCommentByPostId(commentPostId);
            return RespBean.ok("查询成功",list);
        }
        return RespBean.error("查询失败，未找到该帖子");
    }

    /**
     * 根据用户id查询该用户下全部评论
     * @param commentMemberId
     * @return RespBean
     */
    @GetMapping("/selectAllCommentByMemberId")
    @ApiOperation(value = "根据用户id查询该用户下全部评论", notes = "用户需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentMemberId",
                    value = "用户id", required = true, dataType = "int")

    }
    )
    public RespBean selectAllCommentByMemberId(int commentMemberId) {
        if (iCommentServicel.selectAllCommentByMemberId(commentMemberId) != null){
            List<CommentBean> list = iCommentServicel.selectAllCommentByMemberId(commentMemberId);
            return RespBean.ok("查询成功",list);
        }
        return RespBean.error("查询失败，未找到该用户");
    }

    /**
     * 查询评论
     * @param commentId
     * @return RespBean
     */
    @GetMapping("/selectComment")
    @ApiOperation(value = "查询评论", notes = "评论需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataType = "int")

    }
    )
    public RespBean selectComment(int commentId) {
        if (iCommentServicel.selectComment(commentId) != null){
            CommentBean commentBean = iCommentServicel.selectComment(commentId);
            return RespBean.ok("查询成功",commentBean);
        }
        return RespBean.error("查询失败，未找到该评论");
    }
    /**
     * 查询全部评论
     * @return RespBean
     */
    @GetMapping("/selectAllComment")
    @ApiOperation(value = "查询全部评论", notes = "评论需存在", httpMethod = "GET")
    public RespBean selectAllComment() {
        List<CommentBean>list = iCommentServicel.selectAllComment();
            return RespBean.ok("查询成功",list);
    }
}
