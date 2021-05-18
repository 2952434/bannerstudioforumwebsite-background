package studio.banner.forumwebsite.controller.background;
import com.sun.corba.se.impl.naming.cosnaming.NamingUtils;
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
import studio.banner.forumwebsite.bean.PostBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IPostService;
import studio.banner.forumwebsite.service.IReplyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * @Author: HYK
 * @Date: 2021/05/10/21:30
 * Despriction 帖子操作接口
 */

@RestController
@Api(tags = "帖子接口",value = "PostController")
public class PostController {
    /**
     * 日志 打印信息
     */
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    protected IPostService iPostService;
    @Autowired
    protected ICommentService iCommentService;
    @Autowired
    protected IReplyService iReplyService;

    /**
     * 帖子接口
     * @param postBean
     * @param bindingResult
     * @return RespBean
     */
    @PostMapping("/insertPost")
    @ApiOperation(value = "帖子增加", notes = "帖子内容不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "帖子发表者id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "postContent",
                    value = "帖子内容", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "postPageview",
                    value = "帖子浏览量", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "postCommentNumber",
                    value = "帖子评论量", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "postTime",
                    value = "帖子创建时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "postForward",
                    value = "帖子是否为转发，0为原创，其他数字为原创作者的id", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "postLikeNumber",
                    value = "帖子点赞数量", required = false, dataType = "int")
    }
    )
    public RespBean insertPost(@Valid PostBean postBean, BindingResult bindingResult) {

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
        iPostService.insertPost(postBean);
        return RespBean.ok("成功");
    }

    /**
     * 帖子转发接口
     * @param postBean
     * @param bindingResult
     * @return RespBean
     */
    @PostMapping("/forwardPost")
    @ApiOperation(value = "帖子转发", notes = "帖子需存在", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "帖子发表者id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "postContent",
                    value = "帖子内容", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "postPageview",
                    value = "帖子浏览量", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "postCommentNumber",
                    value = "帖子评论量", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "postTime",
                    value = "帖子创建时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "postForward",
                    value = "帖子是否为转发，0为原创，其他数字为原创作者的id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "postLikeNumber",
                    value = "帖子点赞数量", required = false, dataType = "int")
    }

    )
    public RespBean forwardPost(@Valid PostBean postBean, BindingResult bindingResult) {
        /**
         * 将@Valid鉴权的错误信息返给前端
         */
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = new HashMap<>(999);
            List<FieldError> errors = bindingResult.getFieldErrors();
            logger.error("转发失败！");
            for (FieldError error : errors) {
                logger.error("错误的字段名：" + error.getField());
                logger.error("错误信息：" + error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
            return RespBean.error(map);
        }
        iPostService.forwardPost(postBean);
        return RespBean.ok("转发成功");
    }


    /**
     *  帖子删除接口
     * @param postId
     * @return RespBean
     */
    @DeleteMapping("/deletePost")
    @ApiOperation(value = "帖子删除", notes = "帖子需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataType = "int"),
    }
    )
    public RespBean deletePost(int postId) {
        if (iPostService.deletePost(postId)) {
            if (iCommentService.selectAllCommentByPostId(postId) != null) {
                List<CommentBean> list = iCommentService.selectAllCommentByPostId(postId);
                for (int i = 0; i < list.size(); i++) {
                    int commentId = list.get(i).getCommentId();
                    iReplyService.deleteAllReplyByCommentId(commentId);
                }
                iCommentService.deleteAllCommnetByPostId(postId);
                return RespBean.ok("删除成功");
            }
        }
        return RespBean.error("删除失败,未查询到该文章");
    }

    /**
     * 根据用户id清空该用户全部帖子
     * @param postMemberId
     * @return RespBean
     */
    @DeleteMapping("/deleteAllPost")
    @ApiOperation(value = "帖子批量删除", notes = "用户需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataType = "String"),
    }
    )
    public RespBean deleteAllPost(int postMemberId) {
        if (iPostService.selectAllPostById(postMemberId) != null) {
            List<PostBean>List = iPostService.selectAllPostById(postMemberId);
            for (int j = 0 ; j < List.size() ; j++){
                if (iCommentService.selectAllCommentByPostId(List.get(j).getPostId()) != null) {
                    List<CommentBean> list = iCommentService.selectAllCommentByPostId(List.get(j).getPostId());
                    for (int i = 0; i < list.size(); i++) {
                        int commentId = list.get(i).getCommentId();
                        iReplyService.deleteAllReplyByCommentId(commentId);
                    }
                }
            }
            iPostService.deleteAllPost(postMemberId);
            iCommentService.deleteAllCommentByMemberId(postMemberId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败,未查询到该用户或用户已无文章");
    }

    /**
     * 帖子内容修改接口
     * @param postId
     * @param newContent
     * @return RespBean
     */
    @PutMapping("/updatePostContent")
    @ApiOperation(value = "帖子内容修改", notes = "帖子需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "newContent",
                    value = "帖子新内容", required = true, dataType = "String"),

    }
    )
    public RespBean udpatePostContent(int postId, String newContent) {
        if (iPostService.updatePostContent(postId, newContent)) {
            return RespBean.ok("成功");
        }
        return RespBean.error("更改失败，未查询到改帖子");
    }

    /**
     * 更改帖子浏览量
     * @param postId
     * @return RespBean
     */
    @PutMapping("/updatePostPageview")
    @ApiOperation(value = "帖子浏览量修改", notes = "帖子需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataType = "int"),

    }

    )
    public RespBean udpatePostPageview(int postId) {
        if (iPostService.updatePostpageview(postId)) {
            return RespBean.ok("成功");
        }
        return RespBean.error("更改失败，未查询到改帖子");
    }

    /**
     * 修改帖子评论量
     * @param postId
     * @return RespBean
     */
    @PutMapping("/updatePostCommentNumber")
    @ApiOperation(value = "帖子评论量修改", notes = "帖子需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataType = "int"),
    }
    )
    public RespBean udpatePostCommentNumber(int postId) {
        if (iPostService.updatePostCommentNumber(postId)){
            return RespBean.ok("成功");
        }
        return RespBean.error("更改失败，未查询到改帖子");
    }

    /**
     * 修改帖子点赞量
     * @param postId
     * @return RespBean
     */
    @PutMapping("/updatePostLikeNumber")
    @ApiOperation(value = "帖子点赞量修改", notes = "帖子需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataType = "int"),
    }
    )
    public RespBean udpatePostLikeNumber(int postId) {
        if (iPostService.updatePostLikeNumber(postId)){
            return RespBean.ok("成功");
        }
        return RespBean.error("更改失败，未查询到改帖子");
    }

    /**
     * 根据帖子id查找帖子
     * @param postId
     * @return RespBean
     */
    @GetMapping("/selectPost")
    @ApiOperation(value = "帖子查找", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataType = "int"),
    }
    )
    public RespBean selectPost(int postId) {
        if (iPostService.selectPost(postId) != null) {
            PostBean postBean = iPostService.selectPost(postId);
            return RespBean.ok("成功",postBean);
        }
        return RespBean.error("查找失败，未查询到该帖子 ");
    }

    /**
     * 根据用户id查询该用户所有帖子
     * @param postMemberId
     * @return RespBean
     */
    @GetMapping("/selectAllPostById")
    @ApiOperation(value = "查询某用户所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataType = "int"),
    }
    )
    public RespBean selectAllPostById(int postMemberId) {
        if (iPostService.selectAllPostById(postMemberId).size() != 0) {
            List<PostBean> list = iPostService.selectAllPostById(postMemberId);
            return RespBean.ok("成功",list);
        }
        return RespBean.error("查找失败，未查询到该帖子 ");
    }
    /**
     * 查询全部帖子接口
     * @return RespBean
     */
    @GetMapping("/selectAllPost")
    @ApiOperation(value = "查找所有帖子", notes = "帖子需存在", httpMethod = "GET")
    public RespBean selectAllPost() {
        List<PostBean>list = iPostService.selectAllPost();
        return RespBean.ok("成功",list);
    }
}
