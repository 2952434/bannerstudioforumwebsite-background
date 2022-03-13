package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.service.IUserGradeService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/12/12 20:45
 * @role:
 */
@RestController
@Api(tags = "后台用户年级姓名方向接口", value = "BUserGradeController")
@RequestMapping("/backGround")
public class BUserGradeController {

    @Autowired
    private IUserGradeService userGradeService;

    @GetMapping("/userGradeBackGround/judgeUserGradeNameById")
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


    @PostMapping("/userGradeBackGround/insertUserGradeDirection")
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
            return RespBean.error("设置失败！！！");
        }
    }

    @GetMapping("/userGradeBackGround/selectUserGradeById")
    @ApiOperation(value = "根据用户id查询所属班级", notes = "用户id不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserGradeById(Integer userId) {
        UserGradeBean userGradeBean = userGradeService.selectUserGradeById(userId);
        if (userGradeBean == null) {
            return RespBean.error("查询用户班级失败");
        } else {
            return RespBean.ok("查询成功", userGradeBean);
        }
    }

    @GetMapping("/userGradeBackGround/selectUserNameById")
    @ApiOperation(value = "根据用户id查询姓名", notes = "用户id不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserNameById(Integer userId) {
        UserNameBean userNameBean = userGradeService.selectUserNameById(userId);
        if (userNameBean == null) {
            return RespBean.error("查询用户班级失败");
        } else {
            return RespBean.ok("查询成功", userNameBean);
        }
    }

    @GetMapping("/userGradeBackGround/selectUserDirectionById")
    @ApiOperation(value = "根据用户id查询该用户方向", notes = "用户id不为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserDirectionById(Integer userId) {
        UserDirectionBean userDirectionBean = userGradeService.selectUserDirectionById(userId);
        if (userDirectionBean == null) {
            return RespBean.error("查询用户班级失败");
        } else {
            return RespBean.ok("查询成功", userDirectionBean);
        }
    }

    @GetMapping("/userGradeBackGround/selectUserNameByGrade")
    @ApiOperation(value = "根据年级查询该年级的所有用户", notes = "年级不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "grade",
            value = "年级", required = true, dataTypeClass = String.class)
    public RespBean selectUserNameByGrade(String grade) {
        List<UserNameBean> list = userGradeService.selectUserNameByGrade(grade);
        if (list == null) {
            return RespBean.error("查询失败");
        } else {
            return RespBean.ok("查询成功", list);
        }
    }

    @GetMapping("/userGradeBackGround/selectUserNameByDirection")
    @ApiOperation(value = "根据方向查询用户姓名", notes = "方向不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "direction",
            value = "方向名", required = true, dataTypeClass = String.class)
    public RespBean selectUserNameByDirection(String direction) {
        List<UserNameBean> userNameBeans = userGradeService.selectUserNameByDirection(direction);
        if (userNameBeans == null) {
            return RespBean.error("查询失败！！！");
        } else {
            return RespBean.ok("查询成功", userNameBeans);
        }
    }

    @GetMapping("/userGradeBackGround/selectUserContactByUserName")
    @ApiOperation(value = "跟据姓名查询该用户关系id", notes = "用户姓名不能为空", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "userName",
            value = "用户姓名", required = true, dataTypeClass = String.class)
    public RespBean selectUserContactByUserName(String userName) {
        List<UserGradeContactBean> userGradeContactBeans = userGradeService.selectUserContactByUserName(userName);
        if (userGradeContactBeans == null) {
            return RespBean.error("查询失败！！！");
        } else {
            return RespBean.ok("查询成功", userGradeContactBeans);
        }
    }

    @GetMapping("/userGradeBackGround/selectUserNameByDirectionAndGrade")
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
            return RespBean.error("查询失败！！！");
        } else {
            return RespBean.ok("查询成功", userNameBeans);
        }
    }

    @DeleteMapping("/userGradeBackGround/deleteUserById")
    @ApiOperation(value = "根据用户id删除该用户")
    @ApiImplicitParam(type = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteUserById(Integer userId) {
        boolean deleteUserById = userGradeService.deleteUserById(userId);
        if (deleteUserById) {
            return RespBean.ok("删除成功！！！");
        } else {
            return RespBean.error("删除失败！！！");
        }
    }


    @PostMapping("/userGradeBackGround/insertUserGrade")
    @ApiOperation(value = "增加用户年级", notes = "用户年级不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id",
                    value = "id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "userGrade",
                    value = "要增加的年级", required = true, dataTypeClass = String.class)
    })
    public RespBean insertUserGrade(UserGradeBean userGradeBean) {
        boolean insertUserGrade = userGradeService.insertUserGrade(userGradeBean);
        if (insertUserGrade) {
            return RespBean.ok("添加成功！！！");
        } else {
            return RespBean.error("添加失败！！！");
        }
    }

    @GetMapping("/userGradeBackGround/selectAllUserGrade")
    @ApiOperation(value = "查询所有年级", httpMethod = "GET")
    public RespBean selectAllUserGrade() {
        List<UserGradeBean> userGradeBeans = userGradeService.selectAllUserGrade();
        return RespBean.ok("查询成功", userGradeBeans);
    }

    @PostMapping("/userGradeBackGround/updateUserGradeById")
    @ApiOperation(value = "通过年级id更改年级", notes = "id和年级不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id",
                    value = "年级id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "userGrade",
                    value = "年级内容", required = true, dataTypeClass = String.class)
    })
    public RespBean updateUserGradeById(UserGradeBean userGradeBean) {
        boolean updateUserGrade = userGradeService.updateUserGradeById(userGradeBean);
        if (updateUserGrade) {
            return RespBean.ok("修改成功!!!");
        } else {
            return RespBean.error("修改失败！！！");
        }
    }

    @DeleteMapping("/userGradeBackGround/deleteUserGradeById")
    @ApiOperation(value = "根据年级id删除年级", notes = "id不能为空", httpMethod = "DELETE")
    @ApiImplicitParam(type = "query", name = "gradeId",
            value = "年级id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteUserGradeById(Integer gradeId) {
        boolean deleteUserGradeById = userGradeService.deleteUserGradeById(gradeId);
        if (deleteUserGradeById) {
            return RespBean.ok("删除成功！！！");
        } else {
            return RespBean.error("删除失败！！！");
        }
    }

    @PostMapping("/userGradeBackGround/insertUserDirection")
    @ApiOperation(value = "管理员添加学习方向", notes = "学习方向不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id",
                    value = "方向id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "userDirection",
                    value = "学习方向", required = true, dataTypeClass = String.class)
    })
    public RespBean insertUserDirection(UserDirectionBean userDirectionBean) {
        boolean insertUserDirection = userGradeService.insertUserDirection(userDirectionBean);
        if (insertUserDirection) {
            return RespBean.ok("添加成功！！！");
        } else {
            return RespBean.error("该方向已存在，添加失败！！！");
        }
    }

    @GetMapping("/userGradeBackGround/selectAllUserDirection")
    @ApiOperation(value = "查询所有学习方向", httpMethod = "GET")
    public RespBean selectAllUserDirection() {
        List<UserDirectionBean> userDirectionBeans = userGradeService.selectAllUserDirection();
        return RespBean.ok("查询成功", userDirectionBeans);
    }

    @PostMapping("/userGradeBackGround/updateUserDirectionById")
    @ApiOperation(value = "管理员更改学习方向")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "id",
                    value = "学习方向id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "userDirection",
                    value = "学习方向", required = true, dataTypeClass = String.class)
    })
    public RespBean updateUserDirectionById(UserDirectionBean userDirectionBean) {
        boolean updateUserDirectionById = userGradeService.updateUserDirectionById(userDirectionBean);
        if (updateUserDirectionById) {
            return RespBean.ok("更改成功！！！");
        } else {
            return RespBean.error("该方向已存在，更改失败！！！");
        }
    }

    @DeleteMapping("/userGradeBackGround/deleteUserDirectionById")
    @ApiOperation(value = "管理员根据学习方向id删除该学习方向", notes = "id不能为空", httpMethod = "DELETE")
    @ApiImplicitParam(type = "query", name = "userDirectionId",
            value = "学习方向id", required = true, dataTypeClass = Integer.class)
    public RespBean deleteUserDirectionById(Integer userDirectionId) {
        boolean deleteUserDirectionById = userGradeService.deleteUserDirectionById(userDirectionId);
        if (deleteUserDirectionById) {
            return RespBean.ok("删除成功！！！");
        } else {
            return RespBean.error("删除失败！！！");
        }
    }

    @PostMapping("/userGradeBackGround/updateUserDirectionByUserId")
    @ApiOperation(value = "管理员根据用户id更改用户所选方向", notes = "用户id和用户方向不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "userDirection",
                    value = "学习方向", required = true, dataTypeClass = String.class)
    })
    public RespBean updateUserDirectionByUserId(Integer userId, String userDirection) {
        boolean updateUserDirectionByUserId = userGradeService.updateUserDirectionByUserId(userId, userDirection);
        if (updateUserDirectionByUserId) {
            return RespBean.ok("修改成功");
        } else {
            return RespBean.error("修改失败,请检查输入是否正确！！！");
        }
    }

    @PostMapping("/userGradeBackGround/updateUserNameByUserId")
    @ApiOperation(value = "管理员根据用户id更改用户姓名", notes = "用户id和用户名不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "id",
                    value = "姓名id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "userName",
                    value = "用户姓名", required = true, dataTypeClass = String.class)
    })
    public RespBean updateUserNameByUserId(Integer userId, UserNameBean userNameBean) {
        boolean updateUserNameByUserId = userGradeService.updateUserNameByUserId(userId, userNameBean);
        if (updateUserNameByUserId) {
            return RespBean.ok("修改成功！！！");
        } else {
            return RespBean.error("修改失败，该用户已存在！！！");
        }
    }

    @PostMapping("/userGradeBackGround/updateUserGradeByUserId")
    @ApiOperation(value = "管理员根据用户id更改用户所在年级", notes = "用户id和用户方向不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(type = "query", name = "userGrade",
                    value = "年级", required = true, dataTypeClass = String.class)
    })
    public RespBean updateUserGradeByUserId(Integer userId, String userGrade) {
        boolean updateUserGradeByUserId = userGradeService.updateUserGradeByUserId(userId, userGrade);
        if (updateUserGradeByUserId) {
            return RespBean.ok("修改成功！！！");
        } else {
            return RespBean.error("修改失败，请检查输入格式是否正确！！！");
        }
    }

    @GetMapping("/userGradeBackGround/selectDimUserContact")
    @ApiOperation(value = "模糊查询用户信息", notes = "模糊查询字段需为年级、姓名、或方向其中一项", httpMethod = "GET")
    @ApiImplicitParam(type = "query", name = "dim",
            value = "模糊查询字段", required = true, dataTypeClass = String.class)
    public RespBean selectDimUserContact(String dim) {
        List<UserGradeContactBean> userGradeContactBeans = userGradeService.selectDimUserName(dim);
        if (userGradeContactBeans.size() != 0) {
            return RespBean.ok("查询成功", userGradeContactBeans);
        } else {
            return RespBean.error("未查询到该用户", userGradeContactBeans);
        }
    }


}
