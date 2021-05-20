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
import studio.banner.forumwebsite.bean.UserContactBean;
import studio.banner.forumwebsite.service.IUserContactService;
import studio.banner.forumwebsite.service.IUserMsgService;
import studio.banner.forumwebsite.service.IUserService;

import java.util.List;

/**
 * @Author: ljh
 * @Date: 2021/5/18 18:30
 */
@RestController
@Api(tags = "关注接口", value = "UserContactController")
public class UserContactController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    protected IUserContactService iUserContactService;

    @ApiOperation(value = "新增关注", notes = "已关注过无法再次关注", httpMethod = "POST")
    @PostMapping("/insertContact")
    public RespBean insert(UserContactBean userContactBean) {
        if (iUserContactService.contacted(userContactBean.getMemberFan(),userContactBean.getMemberStar()) == null) {
            iUserContactService.insertContact(userContactBean);
            logger.info(userContactBean.getMemberFan() + "成功关注" + userContactBean.getMemberStar());
            return RespBean.ok(userContactBean.getMemberFan() + "成功关注" + userContactBean.getMemberStar());
        } else {
            logger.info(userContactBean.getMemberFan() + "已关注" + userContactBean.getMemberStar());
            return RespBean.error(userContactBean.getMemberFan() + "已关注" + userContactBean.getMemberStar());
        }
    }

    @ApiOperation(value = "取消关注", notes = "未关注过无法取消关注", httpMethod = "DELETE")
    @DeleteMapping("/deleteContact")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberFan",
                    value = "memberFan", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "memberStar",
                    value = "memberStar", required = true, dataType = "Integer"),
    })
    public RespBean deleteContact(Integer memberFan,Integer memberStar){
        if (iUserContactService.contacted(memberFan,memberStar).size() != 0){
            System.out.println(iUserContactService.contacted(memberFan,memberStar));
            boolean judgment = iUserContactService.deleteContact(iUserContactService.contacted(memberFan,memberStar).get(0).getAttentionId());
            if (judgment){
                logger.info("取消关注成功");
                return RespBean.ok("取消关注成功");
            }else {
                logger.error("取消关注失败");
                return RespBean.error("取消关注失败");
            }
        }else{
            logger.error("未关注过该用户");
            return RespBean.error("未关注过该用户");
        }
    }

    @ApiOperation(value = "查询是否存在关注关系", notes = "返回对象", httpMethod = "GET")
    @GetMapping("/selectContact")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberFan",
                    value = "memberFan", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "memberStar",
                    value = "memberStar", required = true, dataType = "Integer"),
    })
    public List<UserContactBean> selectContact(Integer memberFan, Integer memberStar){
        if (iUserContactService.contacted(memberFan,memberStar) != null) {
            logger.info("存在关注关系");
            return iUserContactService.contacted(memberFan,memberStar);
        }
        logger.info("不存在关注关系");
        return null;
        }
    }

