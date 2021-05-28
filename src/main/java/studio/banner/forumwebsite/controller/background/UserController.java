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
import studio.banner.forumwebsite.manager.SendMail;
import studio.banner.forumwebsite.service.IUserMsgService;
import studio.banner.forumwebsite.service.IUserService;

import javax.servlet.http.HttpSession;

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
        if (iUserService.selectAccount(userBean.getMemberPhone())) {
            UserBean newUser = iUserService.selectUser(userBean.getMemberPhone());
            String phone = userBean.getMemberPhone();
            System.out.println(userBean.getMemberPhone()+"*****************");
            String judge1 = "(^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$)";
            if (!phone.matches(judge1)){
                    logger.error("手机号不符合要求");
                    return RespBean.error("手机号不符合要求");
            }
            String mail = userBean.getMemberMail();
            System.out.println(userBean.getMemberMail()+"*****************");
            String judge2 = "(^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$)";
            if (!mail.matches(judge2)) {
                logger.error("邮箱不符合要求");
                return RespBean.error("邮箱不符合要求");
            }
            String pass = userBean.getMemberPassword();
            String judge3 = "(^[a-zA-Z]\\w{5,17}$)";
            if (!pass.matches(judge3)) {
                logger.error("密码不符合要求(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)");
                return RespBean.error("密码不符合要求(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)");
            }
            if (iUserService.insertUser(userBean)) {
                logger.info("成员" + userBean.getMemberPhone() + "添加成功");
            }
            return RespBean.ok("成员" + userBean.getMemberPhone() + "添加成功");
        } else {
            logger.info("成员" + userBean.getMemberPhone() + "添加失败");
            return RespBean.error("成员" + userBean.getMemberPhone() + "添加失败");
        }
    }

    @ApiOperation(value = "登录用户", notes = "查询要登陆的账号是否存在")
    @GetMapping("/selectUser")
    public RespBean select(String memberPhone,String memberPassword){
        boolean hasUser = iUserService.selectUser(memberPhone,memberPassword);
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
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "memberPassword",
                    value = "原用户密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "newMemberPassword",
                    value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "repeatPassword",
                    value = "重复密码", required = true, dataType = "String"),
    }
    )
    public RespBean updateUserPassWord(Integer memberId,String memberPassword, String newMemberPassword,String repeatPassword) {
        if (iUserService.updateUserPassWord(memberId,memberPassword, newMemberPassword, repeatPassword) == true) {
            logger.info("密码修改成功");
            return RespBean.ok("密码修改成功");
        }
        logger.info("密码修改失败");
        return RespBean.error("密码更改失败");
    }

    @ApiOperation(value = "忘记密码", notes = "根据邮箱更改密码")
    @PutMapping("/forgetPassWord")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberPhone",
                    value = "用户账号", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "code",
                    value = "验证码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "memberMail",
                    value = "用户邮箱", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "newMemberPassword",
                    value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "repeatPassword",
                    value = "重复密码", required = true, dataType = "String"),
    }
    )
    public RespBean forgetPassWord(String memberPhone,String memberMail,String code,String newMemberPassword,String repeatPassword) {
        if (newMemberPassword.equals(repeatPassword)) {

            if (iUserService.forgetPassWord(memberPhone,memberMail,code,newMemberPassword,repeatPassword)){
            return RespBean.ok("密码重置成功");}
            return RespBean.error("密码重置失败");
        }
        return RespBean.error("两次输入的密码不一致");
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
    @PostMapping("/sendEmail")
    @ApiOperation(value = "发送验证码", notes = "邮箱不能为空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "email",
                    value = "邮箱", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户id", required = true, dataType = "int")
    })
    public RespBean sendEmail(String email,HttpSession session){
        boolean judgment = iUserService.sendMail(email,session);
        if (judgment){
            String returnCode = (String) session.getAttribute("code");
            logger.info("发送验证码成功"+session);
            return RespBean.ok("发送验证码成功",returnCode);
        }else{
            logger.error("发送验证码失败");
            return RespBean.error("发送验证码失败");
        }
    }
}
