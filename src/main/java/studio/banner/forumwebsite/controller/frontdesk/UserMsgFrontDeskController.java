package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UserMsgBean;
import studio.banner.forumwebsite.service.IUserMsgService;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mo
 * @Date: 2021/5/17 17:54
 */

@RestController
@Api(tags = "前台用户信息接口", value = "UserMsgFrontDeskController")
public class UserMsgFrontDeskController {
    private static final Logger logger = LoggerFactory.getLogger(UserMsgFrontDeskController.class);

    @Autowired
    protected IUserMsgService iUserMsgService;

    @ApiOperation(value = "初始化用户信息", notes = "用户默认信息都为空", httpMethod = "POST")
    @PostMapping("/userMsgFrontDesk/insertUserMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id",   dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberName",
                    value = "用户昵称",  dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "memberSex",
                    value = "用户性别",  dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "memberAge",
                    value = "用户年龄",  dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberTime",
                    value = "注册时间",  dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "memberHead",
                    value = "用户头像",  dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "memberFans",
                    value = "用户粉丝数",  dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberFans",
                    value = "用户粉丝数", dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberAttention",
                    value = "用户关注数",  dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberBirthday",
                    value = "格式必须为xxxx-xx-xx", required = false, dataTypeClass = Date.class),
            @ApiImplicitParam(paramType = "query", name = "memberSignature",
                    value = "个性签名",  dataTypeClass = String.class)

    }
    )

    public RespBean insert(UserMsgBean userMsgBean) {

        try {
            if (iUserMsgService.insertUserMsg(userMsgBean)) {
                logger.info("Id为" + userMsgBean.getMemberId() + "的用户数据初始化成功");
                return RespBean.ok("Id为" + userMsgBean.getMemberId() + "的用户数据初始化成功");
            } else {
                logger.info("Id为" + userMsgBean.getMemberId() + "的用户数据初始化失败");
                return RespBean.error("Id为" + userMsgBean.getMemberId() + "的用户数据初始化失败");
            }
        } catch (IllegalArgumentException e) {
            return RespBean.error("日期格式错误！！！");
        }
    }

    @ApiOperation(value = "根据Id更改昵称", notes = "将原用户昵称改为新的昵称")
    @PutMapping("/userMsgFrontDesk/updateUserName")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberName",
                    value = "新昵称", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean updateUserName(Integer memberId, String memberName) {
        if (iUserMsgService.updateUserName(memberId, memberName) == true) {
            return RespBean.ok("昵称更改成功");
        }
        return RespBean.error("昵称更改失败");
    }

    @ApiOperation(value = "根据Id更改性别", notes = "修改用户的性别信息")
    @PutMapping("/userMsgFrontDesk/updateUserSex")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberSex",
                    value = "性别", required = true, dataTypeClass = String.class),
    }
    )
    public RespBean updateUserSex(Integer memberId, String memberSex) {
        if (iUserMsgService.updateUserSex(memberId, memberSex)) {
            return RespBean.ok("性别修改成功");
        }
        return RespBean.error("性别修改失败");
    }

    @ApiOperation(value = "根据Id更改年龄", notes = "修改用户的年龄信息")
    @PutMapping("/userMsgFrontDesk/updateUserAge")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberAge",
                    value = "年龄", required = true, dataTypeClass = Integer.class),
    }
    )
    public RespBean updateUserAge(Integer memberId, Integer memberAge) {
        if (iUserMsgService.updateUserAge(memberId, memberAge)) {
            return RespBean.ok("年龄修改成功");
        }
        return RespBean.error("年龄修改失败");
    }

    @ApiOperation(value = "根据Id更改头像", notes = "修改用户的头像地址")
    @PutMapping("/userMsgFrontDesk/updateUserHead")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberHead",
                    value = "新的头像地址值", required = true, dataTypeClass = String.class),
    }
    )
    public RespBean updateUserHead(Integer memberId, String memberHead) {
        if (iUserMsgService.updateUserHead(memberId, memberHead)) {
            return RespBean.ok("头像修改成功");
        }
        return RespBean.error("头像修改失败");
    }


    @ApiOperation(value = "根据Id查询用户信息", notes = "同时更新关注人数和粉丝人数")
    @PutMapping("/userMsgFrontDesk/selectUserMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", required = true, dataTypeClass = Integer.class)
    }
    )
    public RespBean selectUserMsg(Integer memberId) {
        if (iUserMsgService.selectUserMsg(memberId) != null) {
            return RespBean.ok(iUserMsgService.selectUserMsg(memberId));
        }
        return RespBean.error("用户信息查询失败");
    }

    @ApiOperation(value = "根据用户id查询生日")
    @GetMapping("/userMsgFrontDesk/selectUserBirthdayById")
    @ApiImplicitParam(paramType = "query", name = "id",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserBirthdayById(Integer id) {
        boolean birthdayById = iUserMsgService.selectBirthdayById(id);
        if (birthdayById) {
            return RespBean.ok("今天是您的生日，生日快乐！！！！");
        }
        return null;
    }


    @GetMapping("/userMsgFrontDesk/selectUserBirthday")
    @ApiOperation(value = "查询过生日的人")
    @ApiImplicitParam(paramType = "query", name = "memberId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserBirthday(Integer memberId) {
        List<UserMsgBean> list = iUserMsgService.selectBirthday(memberId);
        if (list.size() != 0) {
            return RespBean.ok("今天生日的人为：" + list);
        } else {
            return RespBean.ok("今天没人过生日！！！");
        }
    }

    @PostMapping("/userMsgFrontDesk/blessUserBirthday")
    @ApiOperation(value = "祝福过生日的人")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "过生日人的id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "content",
                    value = "祝福内容", required = true, dataTypeClass = String.class)
    })
    public RespBean blessUserBirthday(Integer memberId, String content) {
        boolean b = iUserMsgService.blessUserBirthday(memberId, content);
        if (b) {
            return RespBean.ok("邮件发送成功！！！");
        } else {
            return RespBean.error("邮件发送失败！！！");
        }
    }

    @PostMapping("/userMsgFrontDesk/updateUserSignature")
    @ApiOperation(value = "更新用户签名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "signature",
                    value = "个性签名",dataTypeClass = String.class)
    })
    public RespBean updateUserSignature(Integer memberId,String signature){
        boolean signature1 = iUserMsgService.updateUserSignature(memberId, signature);
        if (signature1){
            return RespBean.ok("更新成功！！！");
        }else {
            return RespBean.error("更新失败！！！");
        }
    }
}
