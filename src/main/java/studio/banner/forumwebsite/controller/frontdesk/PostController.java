package studio.banner.forumwebsite.controller.frontdesk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.util.Json;
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
import studio.banner.forumwebsite.service.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HYK
 * @Date: 2021/05/10/21:30
 * Despriction 帖子操作接口
 */

@RestController
@Api(tags = "前台帖子接口", value = "BackPostController")
@RequestMapping("/frontDesk")
public class PostController {
    /**
     * 日志 打印信息
     */
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IPostEsService iPostEsService;
    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private IPostTypeService iPostTypeService;

    /**
     * 测试Markdown
     *
     * @return
     */
//    @Secured("admin")
    @GetMapping("/postFrontDesk/toEditor")
    public ModelAndView toEditor() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editor/editormd");
        return modelAndView;
    }

    @GetMapping("/selectPostAllType")
    @ApiOperation(value = "查询所有帖子类型",httpMethod = "GET")
    public RespBean selectPostAllType() {
        return iPostTypeService.selectPostAllType();
    }
    /**
     * 帖子增加接口
     *
     * @param postBean
     * @param bindingResult
     * @return RespBean
     */
    @PostMapping("/insertPost")
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
    })
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
                return iPostService.insertPost(postBean);
            }
        }
        return RespBean.error("添加帖子失败,帖子内容有误");
    }
    /**
     * 帖子删除接口
     *
     * @param postId
     * @return RespBean
     */

    @DeleteMapping("/deletePost/{postId}")
    @ApiOperation(value = "帖子删除", notes = "帖子需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean deletePost(@PathVariable Integer postId) {
        return iPostService.deletePostById(postId);
    }

    /**
     * 根据用户id清空该用户全部帖子
     *
     * @param postMemberId
     * @return RespBean
     */
    @DeleteMapping("/deleteAllPost/{postMemberId}")
    @ApiOperation(value = "帖子批量删除", notes = "用户需存在", httpMethod = "DELETE")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = String.class),
    })
    public RespBean deleteAllPost(@PathVariable Integer postMemberId) {
        return iPostService.deleteAllPost(postMemberId);
    }

    @PostMapping("/updatePostById")
    @ApiOperation(value = "修改帖子信息", httpMethod = "POST")
    public RespBean updatePostById(PostBean postBean) {
        return iPostService.updatePostById(postBean);
    }


    /**
     * 根据帖子id查找帖子
     *
     * @param postId
     * @return RespBean
     */
    @GetMapping("/selectPost/{postId}")
    @ApiOperation(value = "帖子查找", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postId",
                    value = "帖子id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectPost(@PathVariable Integer postId) {
        PostBean postBean = iPostService.selectPost(postId);
        if (postBean != null) {

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
    @GetMapping("/selectAllPostByDescById/{postMemberId}")
    @ApiOperation(value = "根据时间倒序查询某用户所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectAllPostByDescById(@PathVariable Integer postMemberId) {
        if (iPostService.selectAllPostByDescById(postMemberId) != null) {
            List<PostBean> list = iPostService.selectAllPostByDescById(postMemberId);
            return RespBean.ok("查找成功", list);
        }
        return RespBean.error("查找失败，未查询到该用户或该用户无帖子");
    }

    /**
     * 根据用户id查询该用户所有帖子
     *
     * @param postMemberId
     * @return RespBean
     */
    @GetMapping("/selectAllPostByAscById/{postMemberId}")
    @ApiOperation(value = "根据时间正序查询某用户所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "postMemberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectAllPostByAscById(@PathVariable Integer postMemberId) {
        if (iPostService.selectAllPostByAscById(postMemberId) != null) {
            List<PostBean> list = iPostService.selectAllPostByAscById(postMemberId);
            return RespBean.ok("查找成功", list);
        }
        return RespBean.error("查找失败，未查询到该用户或该用户无帖子");
    }

    @GetMapping("/selectAllPostNum")
    @ApiOperation(value = "查找所有帖子数量", httpMethod = "GET")
    public RespBean selectAllPostNum() {
        return RespBean.ok("查询成功",iPostService.selectAllPostNum());
    }
    /**
     * 查询全部帖子接口
     *
     * @return RespBean
     */
    @GetMapping("/selectAllPost/{page}")
    @ApiOperation(value = "查找所有帖子", notes = "帖子需存在", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class),
    })
    public RespBean selectAllPost(@PathVariable Integer page) {
        IPage<PostBean> iPage = iPostService.selectAllPost(page);
        List<PostBean> list = iPage.getRecords();
        if (list.size() != 0) {
            return RespBean.ok("查询成功", list);
        }
        return RespBean.error("查询失败，未找到该页数");
    }

    @GetMapping("/findAllWithPage/{page}")
    @ApiOperation(value = "分页检索所有帖子", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class)
    })
    public RespBean findAllWithPage(@PathVariable Integer page) {
        return iPostEsService.findAllWithPage(page);
    }


    @GetMapping("/selectDimPost/{page}/{dim}")
    @ApiOperation(value = "全文检索帖子", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page",
                    value = "分页查询页数", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "dim",
                    value = "全文检索字段", required = true, dataTypeClass = String.class)
    })
    public RespBean selectDimPost(@PathVariable Integer page,@PathVariable String dim) {
        return iPostEsService.findByPostContentAndPostTitleAndPostType(dim,page);
    }

    @GetMapping("/add")
    @ApiOperation(value = "帖子排行榜Test", httpMethod = "GET")
    public RespBean add() {
        Set<ZSetOperations.TypedTuple<String>> add = iRedisService.addRedis();
        List<Map<String,String>> list = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> tuple : add) {
            String value = tuple.getValue();
            JSONObject object = JSON.parseObject(value);
            Map<String,String> map1 = new HashMap<>();
            map1.put("postId", (String) object.get("postId"));
            map1.put("postTitle", (String) object.get("postTitle"));
            list.add(map1);
        }
        return RespBean.ok(list);
    }

    @GetMapping("/selectPostRank")
    @ApiOperation(value = "帖子排行榜查询", httpMethod = "GET")
    public RespBean selectPostRank() {
        return RespBean.ok(iRedisService.selectPostRank());
    }


    @PostMapping("/updatePostTopById/{postId}")
    @ApiOperation(value = "根据帖子id实现置顶", notes = "帖子id需存在", httpMethod = "POST")
    @ApiImplicitParam(type = "query", name = "postId",
            value = "帖子id", required = true, dataTypeClass = Integer.class)
    public RespBean updatePostTopById(@PathVariable Integer postId) {
        boolean updatePostTopById = iPostService.updatePostTopById(postId);
        if (updatePostTopById) {
            return RespBean.ok("置顶成功！！！");
        } else {
            return RespBean.error("帖子不存在，置顶失败！！！");
        }
    }

    @PostMapping("/updatePostNoTopById")
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