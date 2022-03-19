package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IFixedInformationService;

/**
 * @Author: Ljx
 * @Date: 2022/3/14 21:30
 * @role:
 */
@RestController
@Api(tags = "后台用户管理", value = "BackUserController")
@RequestMapping("/backGround")
public class BackUserController {


    @Autowired
    private IFixedInformationService iFixedInformationService;

    @GetMapping("/selectAllUserInformation")
    @ApiOperation(value = "查询所有用户信息",httpMethod = "GET")
    public RespBean selectAllUserInformation() {
        return iFixedInformationService.selectAllUserInformation();
    }

    @DeleteMapping("/deleteUsersInformation/{id}")
    @ApiOperation(value = "根据用户id删除该用户",httpMethod = "DELETE")
    @ApiImplicitParam(paramType = "query",name = "id",
            value = "用户id",required = true,dataTypeClass = Integer.class)
    public boolean deleteUsersInformation(@PathVariable("id") Integer id) {
        return iFixedInformationService.deleteUsersInformation(id);
    }

}
