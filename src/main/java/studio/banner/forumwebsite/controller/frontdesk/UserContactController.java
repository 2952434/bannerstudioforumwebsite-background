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
import studio.banner.forumwebsite.bean.UserAttentionBean;
import studio.banner.forumwebsite.service.IUserAttentionService;

import java.util.List;

/**
 * @Author: ljh
 * @Date: 2021/5/18 18:30
 */
@RestController
@Api(tags = "前台关注接口", value = "UserContactController")
@RequestMapping("/frontDesk")
public class UserContactController {
    private static final Logger logger = LoggerFactory.getLogger(UserContactController.class);

    @Autowired
    protected IUserAttentionService iUserAttentionService;

    @ApiOperation(value = "新增关注", notes = "已关注过无法再次关注", httpMethod = "POST")
    @PostMapping("/userContactFrontDesk/insertContact")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "关注主键id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "beAttentionId",
                    value = "被关注的人id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "attentionId",
                    value = "关注者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "contactTime",
                    value = "关注时间", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "attentionShow",
                    value = "关注是否展示0：展示1：不展示", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "attentionInformation",
                    value = "关注信息", required = true, dataTypeClass = String.class)
    })
    public RespBean insert(UserAttentionBean userAttentionBean) {
        if (!iUserAttentionService.contacted(userAttentionBean.getAttentionId(), userAttentionBean.getBeAttentionId())) {
            return iUserAttentionService.insertContact(userAttentionBean);
        } else {
            return RespBean.error("已关注");
        }
    }

    @ApiOperation(value = "取消关注", notes = "未关注过无法取消关注", httpMethod = "DELETE")
    @DeleteMapping("/deleteContact/{memberFan}/{memberStar}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberFan",
                    value = "关注者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberStar",
                    value = "被关注者id", required = true, dataTypeClass = Integer.class),
    })
    public RespBean deleteContact(@PathVariable Integer memberFan,@PathVariable Integer memberStar) {
        if (iUserAttentionService.contacted(memberFan, memberStar)) {
            boolean judgment = iUserAttentionService.deleteContact(memberFan, memberStar);
            if (judgment) {
                logger.info("取消关注成功");
                return RespBean.ok("取消关注成功");
            } else {
                logger.error("取消关注失败");
                return RespBean.error("取消关注失败");
            }
        } else {
            logger.error("未关注过该用户");
            return RespBean.error("未关注过该用户");
        }
    }

    @ApiOperation(value = "查询是否存在关注关系", notes = "返回列表", httpMethod = "GET")
    @GetMapping("/selectContact/{memberFan}/{memberStar}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberFan",
                    value = "关注者id", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "memberStar",
                    value = "被关注者id", required = true, dataTypeClass = Integer.class),
    })
    public boolean selectContact(@PathVariable Integer memberFan,@PathVariable Integer memberStar) {

        return iUserAttentionService.contacted(memberFan, memberStar);

    }

    @ApiOperation(value = "根据用户Id查询其粉丝", notes = "返回列表", httpMethod = "GET")
    @GetMapping("/userContactFrontDesk/selectFan/{memberId}/{page}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberStar",
                    value = "被关注者id", required = true, dataTypeClass = Integer.class),
    })
    public List<UserAttentionBean> selectFan(@PathVariable("memberId") Integer memberStar,@PathVariable("page") Integer page) {

        return iUserAttentionService.fans(memberStar,page);

    }

    @ApiOperation(value = "根据用户Id查询其粉丝数", notes = "返回粉丝数", httpMethod = "GET")
    @GetMapping("/userContactFrontDesk/selectFans/{memberId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberStar",
                    value = "被关注者id", required = true, dataTypeClass = Integer.class),
    })
    public Integer selectFans(@PathVariable("memberId") Integer memberStar) {
        return iUserAttentionService.selectFansByMemberId(memberStar);
    }


    @ApiOperation(value = "根据用户Id查询其关注的人", notes = "返回列表", httpMethod = "GET")
    @GetMapping("/userContactFrontDesk/selectStar/{memberId}/{page}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberFan",
                    value = "被关注者id", required = true, dataTypeClass = Integer.class),
    })
    public List<UserAttentionBean> selectStar(@PathVariable("memberId") Integer memberFan,@PathVariable("page") Integer page) {

        return iUserAttentionService.stars(memberFan,page);

    }

    @ApiOperation(value = "根据用户Id查询其关注的人数", notes = "返回关注的人数", httpMethod = "GET")
    @GetMapping("/userContactFrontDesk/selectStars/{memberId}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberFan",
                    value = "memberFan", required = true, dataTypeClass = Integer.class),
    })
    public Integer selectStars(@PathVariable("memberId") Integer memberFan) {

        return iUserAttentionService.selectStarsByMemberId(memberFan);

    }
}

