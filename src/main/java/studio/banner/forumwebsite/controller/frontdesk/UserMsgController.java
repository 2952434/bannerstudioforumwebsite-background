package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import studio.banner.forumwebsite.bean.*;
import studio.banner.forumwebsite.service.IMemberInformationService;
import studio.banner.forumwebsite.service.IRedisService;
import studio.banner.forumwebsite.service.IFixedInformationService;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mo
 * @Date: 2021/5/17 17:54
 */

@RestController
@Api(tags = "前台用户信息接口", value = "UserMsgController")
@RequestMapping("/frontDesk")
public class UserMsgController {
    private static final Logger logger = LoggerFactory.getLogger(UserMsgController.class);

    @Autowired
    private IMemberInformationService iMemberInformationService;
    @Autowired
    private IRedisService iRedisService;


    @ApiOperation(value = "初始化用户信息", notes = "用户默认信息都为空", httpMethod = "POST")
    @PostMapping("/insertUserMsg")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberName",
                    value = "用户昵称", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "memberSex",
                    value = "用户性别", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "memberAge",
                    value = "用户年龄", dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberTime",
                    value = "注册时间", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "memberHead",
                    value = "用户头像", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "memberFans",
                    value = "用户粉丝数", dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberFans",
                    value = "用户粉丝数", dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberAttention",
                    value = "用户关注数", dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberBirthday",
                    value = "格式必须为xxxx-xx-xx", required = false, dataTypeClass = Date.class),
            @ApiImplicitParam(paramType = "query", name = "memberSignature",
                    value = "个性签名", dataTypeClass = String.class)

    })
    public RespBean insert(MemberInformationBean memberInformationBean) {
        return iMemberInformationService.insertUserMsg(memberInformationBean);
    }

    @ApiOperation(value = "根据Id查询用户信息", notes = "同时更新关注人数和粉丝人数")
    @GetMapping("/selectUserMsg/{memberId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId",
                    value = "用户Id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean selectUserMsg(@PathVariable Integer memberId) {
        if (iMemberInformationService.selectUserMsg(memberId) != null) {
            return RespBean.ok(iMemberInformationService.selectUserMsg(memberId));
        }
        return RespBean.error("用户信息查询失败");
    }

    @ApiOperation(value = "根据用户id查询生日")
    @GetMapping("/selectUserBirthdayById/{id}")
    @ApiImplicitParam(paramType = "query", name = "id",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserBirthdayById(@PathVariable Integer id) {
        boolean birthdayById = iMemberInformationService.selectBirthdayById(id);
        if (birthdayById) {
            return RespBean.ok("今天是您的生日，生日快乐！！！！");
        }
        return null;
    }


    @GetMapping("/selectUserBirthday/{memberId}")
    @ApiOperation(value = "查询过生日的人")
    @ApiImplicitParam(paramType = "query", name = "memberId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUserBirthday(@PathVariable Integer memberId) {
        List<MemberInformationBean> list = iMemberInformationService.selectBirthday(memberId);
        if (list.size() != 0) {
            return RespBean.ok("今天生日的人为：" , list);
        } else {
            return RespBean.ok("今天没人过生日！！！");
        }
    }

    @PostMapping("/blessUserBirthday")
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

    @GetMapping("/selectEveryDayAddViewNum/{memberId}")
    @ApiOperation(value = "根据用户id查询15天每天浏览增长量",httpMethod = "GET")
    @ApiImplicitParam(paramType = "query",name = "memberId",
            value = "用户id",required = true,dataTypeClass = Integer.class)
    public RespBean selectEveryDayAddViewNum(@PathVariable Integer memberId) {
        List<Integer> list = iRedisService.selectEveryDayAddViewNum(memberId);
        if (list.size()==0){
            return RespBean.error("暂无数据");
        }
        return RespBean.ok("查询成功",list);
    }

    @GetMapping("/selectAllInformationByMemberId/{memberId}")
    @ApiOperation(value = "根据用户id查询用户所有信息",httpMethod = "GET")
    @ApiImplicitParam(paramType = "query",name = "memberId",
            value = "用户id",required = true,dataTypeClass = Integer.class)
    public RespBean selectAllInformationByMemberId(@PathVariable Integer memberId){
        return iMemberInformationService.selectAllInformationByMemberId(memberId);
    }
}
