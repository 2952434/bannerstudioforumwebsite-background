package studio.banner.forumwebsite.controller.background;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.service.impl.AdminServiceImpl;

/**
 * @Author: Ljx
 * @Date: 2021/10/23 18:31
 * @role:
 */
@RestController
@Api(tags = "管理员接口",value = "AdminController")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @ApiImplicitParam(paramType = "query",name = "page",
            value = "查询的页数",required = true,dataTypeClass = Integer.class)
    @ApiOperation(value = "查询所有用户",notes = "页数不为空",httpMethod = "GET")
    @GetMapping("/selectAllUser")
    public RespBean selectAllUser(int page){
        Page<UserBean> page1 = adminService.selectAllUser(page);
        return RespBean.ok(page1);
    }

    @ApiImplicitParam(paramType = "query",name = "uid",
            value = "用户id",required = true,dataTypeClass = Integer.class)
    @ApiOperation(value = "删除用户",notes = "用户id不能为空",httpMethod = "DELETE")
    @DeleteMapping("/deleteUserById")
    public RespBean deleteUserById(int uid){
        if (adminService.deleteById(uid)){
            return RespBean.ok("删除用户成功");
        }else {
            return RespBean.error("删除用户失败");
        }
    }
}
