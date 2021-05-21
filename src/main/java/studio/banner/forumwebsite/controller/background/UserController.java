package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserBean;
import studio.banner.forumwebsite.service.IUserMsgService;
import studio.banner.forumwebsite.service.IUserService;

/**
 * @Author: Ljx
 * @Date: 2021/5/7 15:20
 */
@RestController
@Api(tags = "用户接口", value = "UserController")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    protected IUserService iUserService;
    @Autowired
    protected IUserMsgService iUserMsgService;

    @ApiOperation(value = "注册用户", notes = "用户对象不能为空", httpMethod = "POST")
    @PostMapping("/insertUser")
    public RespBean insert(UserBean userBean) {
        if (iUserService.insertUser(userBean)) {
            UserBean newUser = iUserService.selectUser(userBean.getMemberAccountNumber());

            String phone = userBean.getMemberPhone();
            String judge = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";
            if (!phone.matches(judge)) {
                logger.error("电话号不符合要求");
                return RespBean.error("电话号不符合要求");
            }
            iUserMsgService.insertUserMsg(newUser.getMemberId(), null,null,0,null,null,0,0);
            logger.info("成员" + userBean.getMemberAccountNumber() + "添加成功");
            return RespBean.ok("成员" + userBean.getMemberAccountNumber() + "添加成功");
        } else {
            logger.info("成员" + userBean.getMemberAccountNumber() + "添加失败");
            return RespBean.error("成员" + userBean.getMemberAccountNumber() + "添加失败");
        }
    }

    @ApiOperation(value = "登录用户", notes = "查询要登陆的账号是否存在")
    @GetMapping("/selectUser")
    public RespBean select(Integer memberAccountNumber,String memberPassword){
        boolean hasUser = iUserService.selectUser(memberAccountNumber,memberPassword);
        if (hasUser == true){
            logger.info("查询成功");
            return RespBean.ok("账号密码正确");        }else {
            logger.error("查询失败");
            return RespBean.error("账号或密码错误");
        }
    }

    @ApiOperation(value = "更改密码", notes = "将原用户密码改为新的密码")
    @PutMapping("/updateUserPassWord")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberAccountNumber",
                    value = "用户账号", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "memberPassword",
                    value = "原用户密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "newMemberPassword",
                    value = "新密码", required = true, dataType = "String"),
    }
    )
    public RespBean updateUserPassWord(Integer memberId,String memberPassword, String newMemberPassword) {
        if (iUserService.updateUserPassWord(memberId,memberPassword, newMemberPassword) == true) {
            return RespBean.ok("成功");
        }
        return RespBean.error("密码更改失败");
    }

    @ApiOperation(value = "忘记密码", notes = "根据手机号更改密码")
    @PutMapping("/forgetPassWord")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberAccountNumber",
                    value = "用户账号", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "memberPhone",
                    value = "用户手机号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "newMemberPassword",
                    value = "新密码", required = true, dataType = "String"),
    }
    )
    public RespBean forgetPassWord(String memberAccountNumber,String memberPhone, String newMemberPassword) {
        if (iUserService.forgetPassWord(memberAccountNumber,memberPhone,newMemberPassword)) {
            return RespBean.ok("成功");
        }
        return RespBean.error("密码更改失败");
    }

    @ApiOperation(value = "注销用户", notes = "根据Id删除用户", httpMethod = "DELETE")
    @DeleteMapping("/deleteUser")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "id", required = true, dataType = "Integer"),
    })
    public RespBean deleteUser(Integer id){
        if (id!=null){
            boolean judgment = iUserService.deleteUser(id);
            if (judgment){
                logger.info("删除成功");
                return RespBean.ok("删除成功");
            }else {
                logger.error("删除失败");
                return RespBean.error("删除失败");
            }
        }else{
            logger.error("删除失败");
            return RespBean.error("id为空，删除失败");
        }
    }
}
