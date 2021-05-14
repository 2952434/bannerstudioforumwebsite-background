package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserBean;
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

    @ApiOperation(value = "用户注册", notes = "用户对象不能为空", httpMethod = "POST")
    @PostMapping("/insertUser")
    public RespBean insert(UserBean userBean) {
        if (iUserService.insertUser(userBean)) {
            logger.info("成员" + userBean.getMemberAccountNumber() + "添加成功");
            return RespBean.ok("成员" + userBean.getMemberAccountNumber() + "添加成功");
        } else {
            logger.info("成员" + userBean.getMemberAccountNumber() + "添加失败");
            return RespBean.error("成员" + userBean.getMemberAccountNumber() + "添加失败");
        }
    }
    @ApiOperation(value = "登录用户", notes = "查询要登陆的账号是否存在登录")
    @GetMapping("/selectUser")
    public RespBean select(Integer memberAccountNumber,String memberPassword){
        boolean hasUser = iUserService.selectUser(memberAccountNumber,memberPassword);
        if (hasUser == true){
            logger.info("查询成功");
            return RespBean.ok("账号密码正确");
        }else {
            logger.error("查询失败");
            return RespBean.error("账号或密码错误");
        }
    }
}
