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
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.service.ICommentService;
import studio.banner.forumwebsite.service.IPostService;
import studio.banner.forumwebsite.service.IPostTypeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/10/21:30
 * Despriction 帖子操作接口
 */

@RestController
@Api(tags = "后台帖子接口", value = "BPostController")
@RequestMapping("/backGround")
public class BPostController {
    private static final Logger logger = LoggerFactory.getLogger(BPostController.class);
    @Autowired
    private IPostService iPostService;
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IPostTypeService iPostTypeService;


    @GetMapping("/selectPostAllType")
    @ApiOperation(value = "查询所有帖子类型",httpMethod = "GET")
    public RespBean selectPostAllType() {
        return iPostTypeService.selectPostAllType();
    }

    @PostMapping("/insertPostType")
    @ApiOperation(value = "增加帖子类型",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "帖子类型主键id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "postType",
                    value = "帖子类型名", required = true, dataTypeClass = String.class)
    })
    public RespBean insertPostType(PostTypeBean postTypeBean) {
        return iPostTypeService.insertPostType(postTypeBean);
    }

    @DeleteMapping("/deletePostTypeById")
    @ApiOperation(value = "根据id删除帖子类型",httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query", name = "typeId",
            value = "帖子类型主键id", required = true, dataTypeClass = Integer.class)
    public RespBean deletePostTypeById(Integer typeId) {
        return iPostTypeService.deletePostTypeById(typeId);

    }

    @PostMapping("/postBackGround/insertPost")
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
            @ApiImplicitParam(paramType = "query",name = "postGrade",
                    value = "帖子年级",required = true,dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "postType",
                    value = "帖子类型", required = false, dataTypeClass = String.class)
    }
    )
    public RespBean insertPost(PostBean postBean,BindingResult bindingResult) {
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
                iPostService.insertPost(postBean);
                return RespBean.ok("添加帖子成功");
            }
        }
        return RespBean.error("添加帖子失败,帖子内容有误");
    }


    @DeleteMapping("/postBackGround/deleteAllPost")
    @ApiOperation(value = "帖子批量删除", notes = "用户需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = String.class),
    })
    public RespBean deleteAllPost(int postMemberId) {
        if (iPostService.selectAllPostByDescById(postMemberId) != null) {
            iPostService.deleteAllPost(postMemberId);
            iCommentService.deleteAllCommentByMemberId(postMemberId);
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败,未查询到该用户或用户已无文章");
    }

    @GetMapping("/postBackGround/selectPost")
    @ApiOperation(value = "帖子查找", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectPost(int postId) {
        if (iPostService.selectPost(postId) != null) {
            PostBean postBean = iPostService.selectPost(postId);
            return RespBean.ok("查找成功", postBean);
        }
        return RespBean.error("查找失败，未查询到该帖子");
    }

    @GetMapping("/postBackGround/selectAllPostByDescById")
    @ApiOperation(value = "根据时间倒序查询某用户所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectAllPostByDescById(int postMemberId) {
        if (iPostService.selectAllPostByDescById(postMemberId) != null) {
            List<PostBean> list = iPostService.selectAllPostByDescById(postMemberId);
            return RespBean.ok("查找成功", list);
        }
        return RespBean.error("查找失败，未查询到该用户或该用户无帖子");
    }


    @GetMapping("/postBackGround/selectAllPostByAscById")
    @ApiOperation(value = "根据时间正序查询某用户所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectAllPostByAscById(int postMemberId) {
        if (iPostService.selectAllPostByAscById(postMemberId) != null) {
            List<PostBean> list = iPostService.selectAllPostByAscById(postMemberId);
            return RespBean.ok("查找成功", list);
        }
        return RespBean.error("查找失败，未查询到该用户或该用户无帖子");
    }


    @GetMapping("/postBackGround/selectAllPost")
    @ApiOperation(value = "查找所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectAllPost(int page) {
        IPage<PostBean> iPage = iPostService.selectAllPost(page);
        List<PostBean> list = iPage.getRecords();
        if (list.size() != 0) {
            return RespBean.ok("查询成功", list);
        }
        return RespBean.error("查询失败，未找到该页数");
    }


    @PostMapping("/postBackGround/updatePostTopById")
    @ApiOperation(value = "根据帖子id实现置顶", notes = "帖子id需存在", httpMethod = "POST")
    @ApiImplicitParam(type = "query", name = "postId",
            value = "帖子id", required = true, dataTypeClass = Integer.class)
    public RespBean updatePostTopById(Integer postId) {
        boolean updatePostTopById = iPostService.updatePostTopById(postId);
        if (updatePostTopById) {
            return RespBean.ok("置顶成功！！！");
        } else {
            return RespBean.error("帖子不存在，置顶失败！！！");
        }
    }

    @PostMapping("/postBackGround/updatePostNoTopById")
    @ApiOperation(value = "根据帖子id取消置顶", notes = "帖子id需存在", httpMethod = "POST")
    @ApiImplicitParam(type = "query", name = "postId",
            value = "帖子id", required = true, dataTypeClass = Integer.class)
    public RespBean updatePostNoTopById(Integer postId) {
        boolean updatePostTopById = iPostService.updatePostNoTopById(postId);
        if (updatePostTopById) {
            return RespBean.ok("取消置顶成功！！！");
        } else {
            return RespBean.error("帖子不存在，取消置顶失败！！！");
        }
    }
}