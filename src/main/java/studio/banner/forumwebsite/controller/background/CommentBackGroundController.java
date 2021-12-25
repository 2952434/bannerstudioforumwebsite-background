package studio.banner.forumwebsite.controller.background;

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
import studio.banner.forumwebsite.bean.CommentBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IPostService;
import studio.banner.forumwebsite.service.IReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ljx
 * @Date: 2021/12/14 20:21
 * @role:
 */
@RestController
@Api(tags = "后台评论接口", value = "CommentBackGroundController")
public class CommentBackGroundController {
    private static final Logger logger = LoggerFactory.getLogger(CommentBackGroundController.class);
    @Autowired
    protected ICommentService iCommentServicel;
    @Autowired
    protected IReplyService iReplyService;
    @Autowired
    protected IPostService iPostService;

    /**
     * 评论增加接口
     *
     * @param commentBean
     * @param bindingResult
     * @return RespBean
     */
    @PostMapping("/commentBackGround/insertComment")
    @ApiOperation(value = "评论增加", notes = "评论内容不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "commentPostId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "commentMemberId",
                    value = "评论发表者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "commentLikeNumber",
                    value = "评论点赞数量", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "commentContent",
                    value = "评论内容2-100字之间", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "commentTime",
                    value = "评论时间", required = true, dataTypeClass = String.class)
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
        if (iPostService.selectPost(commentBean.getCommentPostId()) != null) {
            String judge = "^.{2,100}$";
            if (commentBean.getCommentContent().matches(judge)) {
                iCommentServicel.insertComment(commentBean);
                return RespBean.ok("评论成功");
            }
            return RespBean.error("评论失败，评论内容不符合标准");
        }
        return RespBean.error("评论失败，未找到该帖子");
    }

    /**
     * 根据帖子id删除该帖子下全部评论接口
     *
     * @param commentPostId
     * @return RespBean
     */

    @DeleteMapping("/commentBackGround/deleteAllCommentByPostId")
    @ApiOperation(value = "根据帖子id删除该帖子下全部评论", notes = "帖子需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentPostId",
                    value = "评帖子id", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean deleteAllCommentByPostId(int commentPostId) {
        if (iCommentServicel.selectAllCommentByPostId(commentPostId) != null) {
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
     *
     * @param commentMemberId
     * @return RespBean
     */
    @DeleteMapping("/commentBackGround/deleteAllCommentByMemberId")
    @ApiOperation(value = "根据用户id删除该用户下全部评论", notes = "用户需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentMemberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean deleteAllCommentByMemberId(int commentMemberId) {
        if (iCommentServicel.selectAllCommentByMemberId(commentMemberId) != null) {
            List<CommentBean> list = iCommentServicel.selectAllCommentByMemberId(commentMemberId);
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
     *
     * @param commentId
     * @return RespBean
     */
    @DeleteMapping("/commentBackGround/deleteComment")
    @ApiOperation(value = "评论删除", notes = "评论需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean deleteComment(int commentId) {
        if (iCommentServicel.selectComment(commentId) != null) {
            iReplyService.deleteAllReplyByCommentId(commentId);
            iCommentServicel.deleteComment(commentId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败，未找到该评论");
    }

    /**
     * 评论内容修改
     *
     * @param commentId
     * @param newContent
     * @return RespBean
     */

    @PutMapping("/commentBackGround/updateCommentContent")
    @ApiOperation(value = "评论内容修改", notes = "评论需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "newContent",
                    value = "新评论内容2-100字之间", required = true, dataTypeClass = String.class)

    }
    )
    public RespBean updateCommentContent(int commentId, String newContent) {
        if (iCommentServicel.selectComment(commentId) != null) {
            String judge = "^.{2,100}$";
            if (newContent.matches(judge)) {
                iCommentServicel.updateCommentContent(commentId, newContent);
                return RespBean.ok("修改成功");
            }
            return RespBean.error("修改失败,评论内容不符合标准");
        }
        return RespBean.error("修改失败，未找到该评论");
    }

    /**
     * 评论点赞量修改
     *
     * @param commentId
     * @return RespBean
     */

    @PutMapping("/commentBackGround/updateCommentLikeNumber")
    @ApiOperation(value = "评论点赞量修改", notes = "评论需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class),

    }
    )
    public RespBean updateCommentLikeNumber(int commentId) {
        if (iCommentServicel.selectComment(commentId) != null) {
            iCommentServicel.updateCommentLikeNumber(commentId);
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败，未找到该评论");
    }

    /**
     * 根据帖子id查询该帖子下全部评论
     *
     * @param commentPostId
     * @return RespBean
     */

    @GetMapping("/commentBackGround/selectAllCommentByPostId")
    @ApiOperation(value = "根据帖子id查询该帖子下全部评论", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentPostId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class)

    }
    )
    public RespBean selectAllCommentByPostId(int commentPostId) {
        if (iCommentServicel.selectAllCommentByPostId(commentPostId) != null) {
            List<CommentBean> list = iCommentServicel.selectAllCommentByPostId(commentPostId);
            return RespBean.ok("查询成功", list);
        }
        return RespBean.error("查询失败，未找到该帖子或该帖子下无评论");
    }

    /**
     * 根据用户id查询该用户下全部评论
     *
     * @param commentMemberId
     * @return RespBean
     */
    @GetMapping("/commentBackGround/selectAllCommentByMemberId")
    @ApiOperation(value = "根据用户id查询该用户下全部评论", notes = "用户需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentMemberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class)

    }
    )
    public RespBean selectAllCommentByMemberId(int commentMemberId) {
        if (iCommentServicel.selectAllCommentByMemberId(commentMemberId) != null) {
            List<CommentBean> list = iCommentServicel.selectAllCommentByMemberId(commentMemberId);
            return RespBean.ok("查询成功", list);
        }
        return RespBean.error("查询失败，未找到该用户或该用户下无评论");
    }

    /**
     * 查询评论
     *
     * @param commentId
     * @return RespBean
     */
    @GetMapping("/commentBackGround/selectComment")
    @ApiOperation(value = "查询评论", notes = "评论需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "commentId",
                    value = "评论id", required = true, dataTypeClass = Integer.class)

    }
    )
    public RespBean selectComment(int commentId) {
        if (iCommentServicel.selectComment(commentId) != null) {
            CommentBean commentBean = iCommentServicel.selectComment(commentId);
            return RespBean.ok("查询成功", commentBean);
        }
        return RespBean.error("查询失败，未找到该评论");
    }

    /**
     * 查询全部评论
     *
     * @return RespBean
     */
    @GetMapping("/commentBackGround/selectAllComment")
    @ApiOperation(value = "查询全部评论", notes = "评论需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class)

    }
    )
    public RespBean selectAllComment(int page) {
        IPage<CommentBean> iPage = iCommentServicel.selectAllComment(page);
        List<CommentBean> list = iPage.getRecords();
        if (list.size() != 0) {
            return RespBean.ok("查询成功", list);
        }
        return RespBean.error("查询失败，未找到该页数");
    }
}
