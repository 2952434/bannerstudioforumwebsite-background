package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
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
@Api(tags = "后台用户信息接口", value = "UserMsgBackGroundController")
public class UserMsgBackGroundController {

    @Autowired
    protected IUserMsgService iUserMsgService;

    @PutMapping("/userMsgBackGround/updateUserName")
    @ApiOperation(value = "根据Id更改昵称", notes = "将原用户昵称改为新的昵称")
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

    @PutMapping("/userMsgBackGround/updateUserSex")
    @ApiOperation(value = "根据Id更改性别", notes = "修改用户的性别信息")
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

    @PutMapping("/userMsgBackGround/updateUserAge")
    @ApiOperation(value = "根据Id更改年龄", notes = "修改用户的年龄信息")
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

    @PutMapping("/userMsgBackGround/updateUserHead")
    @ApiOperation(value = "根据Id更改头像", notes = "修改用户的头像地址")
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


    @PutMapping("/userMsgBackGround/selectUserMsg")
    @ApiOperation(value = "根据Id查询用户信息", notes = "同时更新关注人数和粉丝人数")
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


    @GetMapping("/userMsgBackGround/selectUserBirthday")
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

    @PostMapping("/userMsgBackGround/blessUserBirthday")
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

    @PostMapping("/userMsgBackGround/updateUserSignature")
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
