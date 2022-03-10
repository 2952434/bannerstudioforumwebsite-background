package studio.banner.forumwebsite.controller.background;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.MemberInformationBean;
import studio.banner.forumwebsite.service.IMemberInformationService;

import java.util.List;

/**
 * @Author: Mo
 * @Date: 2021/5/17 17:54
 */

@RestController
@Api(tags = "后台用户信息接口", value = "UserMsgBackGroundController")
@RequestMapping("/backGround")
public class UserMsgBackGroundController {

    @Autowired
    protected IMemberInformationService iMemberInformationService;



    @PutMapping("/userMsgBackGround/selectUserMsg")
    @ApiOperation(value = "根据Id查询用户信息", notes = "同时更新关注人数和粉丝人数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectUserMsg(Integer memberId) {
        if (iMemberInformationService.selectUserMsg(memberId) != null) {
            return RespBean.ok(iMemberInformationService.selectUserMsg(memberId));
        }
        return RespBean.error("用户信息查询失败");
    }


    @GetMapping("/userMsgBackGround/selectUserBirthday")
    @ApiOperation(value = "查询过生日的人")
    @ApiImplicitParam(paramType = "query", name = "memberId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserBirthday(Integer memberId) {
        List<MemberInformationBean> list = iMemberInformationService.selectBirthday(memberId);
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
        boolean b = iMemberInformationService.blessUserBirthday(memberId, content);
        if (b) {
            return RespBean.ok("邮件发送成功！！！");
        } else {
            return RespBean.error("邮件发送失败！！！");
        }
    }

}
