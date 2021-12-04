package studio.banner.forumwebsite.controller.background;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.service.impl.AdminServiceImpl;
import studio.banner.forumwebsite.service.impl.PostServiceImpl;

import java.util.Set;

/**
 * @Author: Ljx
 * @Date: 2021/10/23 18:31
 * @role:
 */
@RestController
@Api(tags = "管理员接口", value = "AdminController")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private PostServiceImpl postService;

    @ApiImplicitParam(paramType = "query", name = "page",
            value = "查询的页数", required = true, dataTypeClass = Integer.class)
    @ApiOperation(value = "查询所有用户", notes = "页数不为空", httpMethod = "GET")
    @GetMapping("/selectAllUser")
    public RespBean selectAllUser(int page) {
        Page<UserBean> page1 = adminService.selectAllUser(page);
        return RespBean.ok(page1);
    }

    @ApiImplicitParam(paramType = "query", name = "uid",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    @ApiOperation(value = "删除用户", notes = "用户id不能为空", httpMethod = "DELETE")
    @DeleteMapping("/deleteUserById")
    public RespBean deleteUserById(int uid) {
        if (adminService.deleteById(uid)) {
            return RespBean.ok("删除用户成功");
        } else {
            return RespBean.error("删除用户失败");
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone",
                    value = "手机号", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "passWord",
                    value = "密码", required = true, dataTypeClass = String.class)
    })
    @ApiOperation(value = "管理员登录", notes = "账号密码不能为空", httpMethod = "GET")
    @GetMapping("/loginAdmin")
    public RespBean loginAdmin(String phone, String passWord) {
        boolean b = adminService.loginAdmin(phone, passWord);
        System.out.println(b);
        if (b) {
            return RespBean.ok("登录成功。");
        } else {
            return RespBean.error("您不是管理员登录失败");
        }
    }

    @ApiOperation(value = "更新帖子排行榜", httpMethod = "POST")
    @PostMapping("/updateRedisPostRank")
    public RespBean updateRedisPostRank(){
        Set<ZSetOperations.TypedTuple<String>> typedTuples = postService.updateRedisPostRank();
        if (typedTuples.size()!=0){
            return RespBean.ok("更新成功，今天的排行榜为：", JSON.toJSONString(typedTuples));
        }else {
            return RespBean.error("更新失败！！！");
        }

    }

    @ApiOperation(value = "更新全文检索",httpMethod = "POST")
    @PostMapping("/updateEsPost")
    public RespBean updateEsPost(){
        postService.updateEsPost();
        return RespBean.ok("更新成功！！！");
    }
}
