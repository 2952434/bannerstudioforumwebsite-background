package studio.banner.forumwebsite.controller.background;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.impl.PostServiceImpl;

import java.util.Set;

/**
 * @Author: Ljx
 * @Date: 2021/10/23 18:31
 * @role:
 */
@RestController
@Api(tags = "后台管理员接口", value = "AdminBackGroundController")
@RequestMapping("/backGround")
public class AdminBackGroundController {

    @Autowired
    private PostServiceImpl postService;


    @ApiOperation(value = "更新帖子排行榜", httpMethod = "POST")
    @PostMapping("/adminBackGround/updateRedisPostRank")
    public RespBean updateRedisPostRank() {
        Set<ZSetOperations.TypedTuple<String>> typedTuples = postService.updateRedisPostRank();
        if (typedTuples.size() != 0) {
            return RespBean.ok("更新成功，今天的排行榜为：", JSON.toJSONString(typedTuples));
        } else {
            return RespBean.error("更新失败！！！");
        }

    }

    @ApiOperation(value = "更新全文检索", httpMethod = "POST")
    @PostMapping("/adminBackGround/updateEsPost")
    public RespBean updateEsPost() {
        postService.updateEsPost();
        return RespBean.ok("更新成功！！！");
    }
}
