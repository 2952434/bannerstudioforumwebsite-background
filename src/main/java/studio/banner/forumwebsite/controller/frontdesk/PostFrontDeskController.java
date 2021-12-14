package studio.banner.forumwebsite.controller.frontdesk;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IPostService;
import studio.banner.forumwebsite.service.IPostTypeService;
import studio.banner.forumwebsite.service.IReplyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/10/21:30
 * Despriction 帖子操作接口
 */

@RestController
@Api(tags = "前台帖子接口", value = "PostFrontDeskController")
public class PostFrontDeskController {
    /**
     * 日志 打印信息
     */
    private static final Logger logger = LoggerFactory.getLogger(PostFrontDeskController.class);
    @Autowired
    private IPostService iPostService;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IPostTypeService iPostTypeService;
    @Autowired
    private IReplyService iReplyService;

    /**
     * 测试Markdown
     * @return
     */
//    @Secured("admin")
    @GetMapping("/postFrontDesk/toEditor")
    public ModelAndView toEditor() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editor/editormd");
        return modelAndView;
    }

    /**
     * 帖子增加接口
     *
     * @param postBean
     * @param bindingResult
     * @return RespBean
     */
    @PostMapping("/postFrontDesk/insertPost")
    @ApiOperation(value = "帖子增加", notes = "帖子内容不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "帖子发表者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postTitle",
                    value = "帖子标题2-20字之间", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "postContent",
                    value = "帖子内容<10000字", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "postPageview",
                    value = "帖子浏览量", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postCommentNumber",
                    value = "帖子评论量", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postTime",
                    value = "帖子创建时间", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "postForward",
                    value = "帖子是否为转发，0为原创，其他数字为原创作者的id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postLikeNumber",
                    value = "帖子点赞数量", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postImageAddress",
                    value = "帖子图片地址", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "postType",
                    value = "帖子类型", required = false, dataTypeClass = String.class)
    }
    )
    public RespBean insertPost(PostBean postBean, BindingResult bindingResult,String ...postType) {
        System.out.println(postBean);
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
        String judge1 = "^.{3,20}$";
        String judge2 = "^.{5,10000}$";
        if (postBean.getPostTitle().matches(judge1)) {
            if (postBean.getPostContent().matches(judge2)) {
                iPostService.insertPost(postBean,postType);
                return RespBean.ok("添加帖子成功");
            }
        }
        return RespBean.error("添加帖子失败,帖子内容有误");
    }

    /**
     * 帖子转发接口
     *
     * @param postTime
     * @param postForwardMemberId
     * @param postId
     * @return RespBean
     */
    @PostMapping("/postFrontDesk/forwardPost")
    @ApiOperation(value = "帖子转发", notes = "帖子需存在", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "原帖子id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postForwardMemberId",
                    value = "转发帖子者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postTime",
                    value = "帖子转发时间", required = true, dataTypeClass = String.class),
    }

    )
    public RespBean forwardPost(int postId, int postForwardMemberId, String postTime) {
        if (iPostService.selectPost(postId) != null) {
            PostBean postBean1 = iPostService.selectPost(postId);
            String postContent = postBean1.getPostContent();
            String imageAddress = postBean1.getPostImageAddress();
            int postForward = postBean1.getPostMemberId();
            String postTitle = postBean1.getPostTitle();
            String[] strings = iPostTypeService.selectPostTypeById(postId);
            PostBean postBean = new PostBean(0, postForwardMemberId, postTitle, postContent, postTime, null, null, postForward, null, imageAddress);
            iPostService.insertPost(postBean,strings);
            return RespBean.ok("转发成功");
        }
        return RespBean.error("转发失败，未查询到原帖子");
    }

    /**
     * 帖子删除接口
     *
     * @param postId
     * @return RespBean
     */

    @DeleteMapping("/postFrontDesk/deletePost")
    @ApiOperation(value = "帖子删除", notes = "帖子需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
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

            }
            iCommentService.deleteAllCommnetByPostId(postId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败,未查询到该文章");
    }

    /**
     * 根据用户id清空该用户全部帖子
     *
     * @param postMemberId
     * @return RespBean
     */
    @DeleteMapping("/postFrontDesk/deleteAllPost")
    @ApiOperation(value = "帖子批量删除", notes = "用户需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = String.class),
    }
    )
    public RespBean deleteAllPost(int postMemberId) {
        if (iPostService.selectAllPostById(postMemberId) != null) {
            List<PostBean> List = iPostService.selectAllPostById(postMemberId);
            for (int j = 0; j < List.size(); j++) {
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
     * 根据帖子id修改帖子标题
     *
     * @param postId
     * @param newTitle
     * @return RespBean
     */
    @PutMapping("/postFrontDesk/updatePostTitle")
    @ApiOperation(value = "帖子标题修改", notes = "帖子需存在,标题长度需在5-20个字符之间", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "newTitle",
                    value = "帖子新标题", required = true, dataTypeClass = String.class),

    }
    )
    public RespBean udpatePostTitle(int postId, String newTitle) {
        if (iPostService.selectPost(postId) != null) {
            String judge = "^.{5,20}$";
            if (newTitle.matches(judge)) {
                iPostService.updatePostTitle(postId, newTitle);
                return RespBean.ok("更改成功");
            }
            return RespBean.error("更改失败,标题不符合规则");
        }
        return RespBean.error("更改失败，未查询到改帖子");
    }


    /**
     * 帖子内容修改接口
     *
     * @param postId
     * @param newContent
     * @return RespBean
     */
    @PutMapping("/postFrontDesk/updatePostContent")
    @ApiOperation(value = "帖子内容修改", notes = "帖子需存在,帖子内容需小于10000字", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "newContent",
                    value = "帖子新内容", required = true, dataTypeClass = String.class),

    }
    )
    public RespBean udpatePostContent(int postId, String newContent) {
        if (iPostService.selectPost(postId) != null) {
            String judge = "^.{1,10000}$";
            if (newContent.matches(judge)) {
                iPostService.updatePostContent(postId, newContent);
                return RespBean.ok("更改成功");
            }
            return RespBean.error("更改失败,内容不符合规则");
        }
        return RespBean.error("更改失败，未查询到改帖子");
    }

    /**
     * 更改帖子浏览量
     *
     * @param postId
     * @return RespBean
     */
    @PutMapping("/postFrontDesk/updatePostPageview")
    @ApiOperation(value = "帖子浏览量修改", notes = "帖子需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),

    }

    )
    public RespBean udpatePostPageview(int postId) {
        if (iPostService.updatePostpageview(postId)) {
            return RespBean.ok("更改成功");
        }
        return RespBean.error("更改失败，未查询到改帖子");
    }

    /**
     * 修改帖子评论量
     *
     * @param postId
     * @return RespBean
     */
    @PutMapping("/postFrontDesk/updatePostCommentNumber")
    @ApiOperation(value = "帖子评论量修改", notes = "帖子需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean udpatePostCommentNumber(int postId) {
        if (iPostService.updatePostCommentNumber(postId)) {
            return RespBean.ok("更改成功");
        }
        return RespBean.error("更改失败，未查询到改帖子");
    }

    /**
     * 修改帖子点赞量
     *
     * @param postId
     * @return RespBean
     */
    @PutMapping("/postFrontDesk/updatePostLikeNumber")
    @ApiOperation(value = "帖子点赞量修改", notes = "帖子需存在", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean udpatePostLikeNumber(int postId) {
        if (iPostService.updatePostLikeNumber(postId)) {
            return RespBean.ok("更改成功");
        }
        return RespBean.error("更改失败，未查询到改帖子");
    }

    /**
     * 根据帖子id查找帖子
     *
     * @param postId
     * @return RespBean
     */
    @GetMapping("/postFrontDesk/selectPost")
    @ApiOperation(value = "帖子查找", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean selectPost(int postId) {
        if (iPostService.selectPost(postId) != null) {
            PostBean postBean = iPostService.selectPost(postId);
            return RespBean.ok("查找成功", postBean);
        }
        return RespBean.error("查找失败，未查询到该帖子");
    }

    /**
     * 根据用户id查询该用户所有帖子
     *
     * @param postMemberId
     * @return RespBean
     */
    @GetMapping("/postFrontDesk/selectAllPostById")
    @ApiOperation(value = "查询某用户所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean selectAllPostById(int postMemberId) {
        if (iPostService.selectAllPostById(postMemberId) != null) {
            List<PostBean> list = iPostService.selectAllPostById(postMemberId);
            return RespBean.ok("查找成功", list);
        }
        return RespBean.error("查找失败，未查询到该用户或该用户无帖子");
    }

    /**
     * 查询全部帖子接口
     *
     * @return RespBean
     */
    @GetMapping("/postFrontDesk/selectAllPost")
    @ApiOperation(value = "查找所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean selectAllPost(int page) {
        IPage<PostBean> iPage = iPostService.selectAllPost(page);
        List<PostBean> list = iPage.getRecords();
        if (list.size() != 0) {
            return RespBean.ok("查询成功", list);
        }
        return RespBean.error("查询失败，未找到该页数");
    }

    @GetMapping("/postFrontDesk/selectDimPost")
    @ApiOperation(value = "全文检索帖子", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "dim",
                    value = "全文检索字段", required = true, dataTypeClass = String.class)
    })
    public RespBean selectDimPost(int page, String dim) {
        List<PostBeanEs> list = iPostService.selectDimPost(page - 1, dim);
        System.out.println(list);
        if (list.size() != 0) {
            return RespBean.ok("查询成功", list);
        } else {
            IPage<PostBean> iPage1 = iPostService.selectAllPost(page);
            List<PostBean> list1 = iPage1.getRecords();
            if (list1.size() != 0) {
                return RespBean.ok(list1);
            }
        }
        return RespBean.error("未查询到相关内容");
    }


    @GetMapping("/postFrontDesk/add")
    @ApiOperation(value = "RedisTest", httpMethod = "GET")
    public RespBean s() {
        Set<ZSetOperations.TypedTuple<String>> s = iPostService.addRedis();
        return RespBean.ok(s);
    }

    @GetMapping("/postFrontDesk/selectPostRank")
    @ApiOperation(value = "帖子排行榜查询", httpMethod = "GET")
    public RespBean selectPostRank() {
        Set<ZSetOperations.TypedTuple<String>> rank = iPostService.selectPostRank();
        return RespBean.ok(rank);
    }


    @GetMapping("/postFrontDesk/selectYesterdayView")
    @ApiOperation(value = "根据作者id获得昨天帖子浏览总量")
    @ApiImplicitParam(paramType = "query", name = "memberId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectYesterdayView(Integer memberId){
        String view = iPostService.selectYesterdayView(memberId);
        if (view==null){
            view = "0";
        }
        return RespBean.ok("昨天的浏览总量为："+view);
    }
}