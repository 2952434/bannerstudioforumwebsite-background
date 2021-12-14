package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.service.IUserMsgService;
import studio.banner.forumwebsite.service.IUserService;

import java.util.List;

/**
 * @Author: Ljx
 * @Date: 2021/5/7 15:20
 */
@RestController
@Api(tags = "后台用户接口", value = "UserBackGroundController")
public class UserBackGroundController {

    @Autowired
    protected IUserService iUserService;
    @Autowired
    protected IUserMsgService iUserMsgService;

    @ApiOperation(value = "注册用户", notes = "用户对象不能为空", httpMethod = "POST")
    @PostMapping("/userBackGround/insertUser")
    public RespBean insert(UserBean userBean) {
        if (iUserService.selectAccount(userBean.getMemberPhone())) {
            String phone = userBean.getMemberPhone();
            String judge1 = "(^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$)";
            if (!phone.matches(judge1)) {
                return RespBean.error("手机号不符合要求");
            }
            String mail = userBean.getMemberMail();
            String judge2 = "(^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$)";
            if (!mail.matches(judge2)) {
                return RespBean.error("邮箱不符合要求");
            }
            String pass = userBean.getMemberPassword();
            String judge3 = "(^[a-zA-Z]\\w{5,17}$)";
            if (!pass.matches(judge3)) {
                return RespBean.error("密码不符合要求(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)");
            }
            if (iUserService.insertUser(userBean)) {
                return RespBean.ok("成员" + userBean.getMemberPhone() + "添加成功");
            }
        }
        return RespBean.error("成员" + userBean.getMemberPhone() + "添加失败");

    }

    @ApiOperation(value = "更改密码", notes = "将原用户密码改为新的密码")
    @PutMapping("/userBackGround/updateUserPassWord")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberPassword",
                    value = "原用户密码", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "newMemberPassword",
                    value = "新密码", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "repeatPassword",
                    value = "重复密码", required = true, dataTypeClass = String.class),
    }
    )
    public RespBean updateUserPassWord(Integer memberId, String memberPassword, String newMemberPassword, String repeatPassword) {
        String pass = newMemberPassword;
        String judge = "(^[a-zA-Z]\\w{5,17}$)";
        if (!pass.matches(judge)) {
            return RespBean.error("密码不符合要求(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)");
        }
        if (iUserService.updateUserPassWord(memberId, memberPassword, newMemberPassword, repeatPassword) == true) {
            return RespBean.ok("密码修改成功");
        }
        return RespBean.error("密码更改失败");
    }


    @ApiOperation(value = "注销用户", notes = "根据Id删除用户", httpMethod = "DELETE")
    @DeleteMapping("/userBackGround/deleteUser")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean deleteUser(Integer id) {
        if (id != null) {
            boolean judgment = iUserService.deleteUser(id);
            if (judgment) {
                return RespBean.ok("删除成功");
            } else {
                return RespBean.error("删除失败");
            }
        } else {
            return RespBean.error("id为空，删除失败");
        }
    }

}
