package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.service.IUserGradeService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 20:11
 * @role:
 */
@RestController
@Api(tags = "前台用户年级姓名方向接口", value = "UserGradeFrontDeskController")
public class UserGradeFrontDeskController {

    @Autowired
    private IUserGradeService userGradeService;

    @GetMapping("/userGradeFrontDesk/judgeUserGradeNameById")
    @ApiOperation(value = "根据id查询用户是否设置年级和姓名和方向", notes = "用户id不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean judgeUserGradeNameById(Integer userId) {
        boolean grade = userGradeService.judgeUserGradeNameById(userId);
        if (grade) {
            return RespBean.ok("用户未设置年级或姓名", grade);
        } else {
            return RespBean.error("用户以设置年级或姓名", grade);
        }
    }


    @PostMapping("/userGradeFrontDesk/insertUserGradeDirection")
    @ApiOperation(value = "用户班级姓名方向添加", notes = "用户班级不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "grade",
                    value = "班级", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "userName",
                    value = "用户姓名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "userDirection",
                    value = "用户方向", required = true, dataTypeClass = String.class)
    })
    public RespBean insertUserGradeDirection(Integer userId, String grade, String userName, String userDirection) {
        boolean userGrade = userGradeService.insertUserGradeDirection(userId, grade, userName, userDirection);
        if (userGrade) {
            return RespBean.ok("设置成功！！！");
        } else {
            return RespBean.error("设置失败，请联系管理员！！！");
        }
    }

    @GetMapping("/userGradeFrontDesk/selectUserGradeById")
    @ApiOperation(value = "根据用户id查询所属班级", notes = "用户id不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserGradeById(Integer userId) {
        UserGradeBean userGradeBean = userGradeService.selectUserGradeById(userId);
        if (userGradeBean == null) {
            return RespBean.error("查询用户班级失败，请联系管理员");
        } else {
            return RespBean.ok("查询成功", userGradeBean);
        }
    }

    @GetMapping("/userGradeFrontDesk/selectUserNameById")
    @ApiOperation(value = "根据用户id查询姓名", notes = "用户id不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserNameById(Integer userId) {
        UserNameBean userNameBean = userGradeService.selectUserNameById(userId);
        if (userNameBean == null) {
            return RespBean.error("查询用户班级失败，请联系管理员");
        } else {
            return RespBean.ok("查询成功", userNameBean);
        }
    }

    @GetMapping("/userGradeFrontDesk/selectUserDirectionById")
    @ApiOperation(value = "根据用户id查询该用户方向", notes = "用户id不为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserDirectionById(Integer userId) {
        UserDirectionBean userDirectionBean = userGradeService.selectUserDirectionById(userId);
        if (userDirectionBean == null) {
            return RespBean.error("查询用户班级失败，请联系管理员");
        } else {
            return RespBean.ok("查询成功", userDirectionBean);
        }
    }

    @GetMapping("/userGradeFrontDesk/selectUserNameByGrade")
    @ApiOperation(value = "根据年级查询该年级的所有用户", notes = "年级不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "grade",
            value = "年级", required = true, dataTypeClass = String.class)
    public RespBean selectUserNameByGrade(String grade) {
        List<UserNameBean> list = userGradeService.selectUserNameByGrade(grade);
        if (list == null) {
            return RespBean.error("查询失败，请联系管理人员");
        } else {
            return RespBean.ok("查询成功", list);
        }
    }

    @GetMapping("/userGradeFrontDesk/selectUserNameByDirection")
    @ApiOperation(value = "根据方向查询用户姓名", notes = "方向不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "direction",
            value = "方向名", required = true, dataTypeClass = String.class)
    public RespBean selectUserNameByDirection(String direction) {
        List<UserNameBean> userNameBeans = userGradeService.selectUserNameByDirection(direction);
        if (userNameBeans == null) {
            return RespBean.error("查询失败，请联系管理员！！！");
        } else {
            return RespBean.ok("查询成功", userNameBeans);
        }
    }

    @GetMapping("/userGradeFrontDesk/selectUserContactByUserName")
    @ApiOperation(value = "跟据姓名查询该用户关系id", notes = "用户姓名不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "userName",
            value = "用户姓名", required = true, dataTypeClass = String.class)
    public RespBean selectUserContactByUserName(String userName) {
        List<UserGradeContactBean> userGradeContactBeans = userGradeService.selectUserContactByUserName(userName);
        if (userGradeContactBeans == null) {
            return RespBean.error("查询失败，请联系管理员！！！");
        } else {
            return RespBean.ok("查询成功", userGradeContactBeans);
        }
    }

    @GetMapping("/userGradeFrontDesk/selectUserNameByDirectionAndGrade")
    @ApiOperation(value = "根据用户方向和年级查询姓名", notes = "用户方向和姓名不能为空", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "userDirection",
                    value = "用户方向", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "userGrade",
                    value = "用户班级", required = true, dataTypeClass = String.class)
    })
    public RespBean selectUserNameByDirectionAndGrade(String userDirection, String userGrade) {
        List<UserNameBean> userNameBeans = userGradeService.selectUserNameByDirectionAndGrade(userDirection, userGrade);
        if (userNameBeans == null) {
            return RespBean.error("查询失败，请联系管理员！！！");
        } else {
            return RespBean.ok("查询成功", userNameBeans);
        }
    }


    @GetMapping("/userGradeFrontDesk/selectAllUserGrade")
    @ApiOperation(value = "查询所有年级", httpMethod = "GET")
    public RespBean selectAllUserGrade() {
        List<UserGradeBean> userGradeBeans = userGradeService.selectAllUserGrade();
        return RespBean.ok("查询成功", userGradeBeans);
    }

    @GetMapping("/userGradeFrontDesk/selectAllUserDirection")
    @ApiOperation(value = "查询所有方向", httpMethod = "GET")
    public RespBean selectAllUserDirection() {
        List<UserDirectionBean> userDirectionBeans = userGradeService.selectAllUserDirection();
        return RespBean.ok("查询成功", userDirectionBeans);
    }
}
