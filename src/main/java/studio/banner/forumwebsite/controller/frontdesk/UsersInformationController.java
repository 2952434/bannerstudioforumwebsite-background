package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import studio.banner.forumwebsite.bean.FixedInformationBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IFixedInformationService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: Ljx
 * @Date: 2021/5/15 16:15
 */
@RestController
@Api(tags = "前台所有用户信息接口", value = "UsersInformationController")
@RequestMapping("/frontDesk")
public class UsersInformationController {
    private static final Logger logger = LoggerFactory.getLogger(UsersInformationController.class);

    @Autowired
    private IFixedInformationService iFixedInformationService;

    @PutMapping("/usersInformationFrontDesk/updateUsersInformation")
    @ApiOperation(value = "用户信息更改", notes = "用户不能为空", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "usersName",
                    value = "用户姓名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersDirection",
                    value = "用户方向", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersPhone",
                    value = "用户手机号", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersQQ",
                    value = "用户QQ号", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersWeChat",
                    value = "用户微信号", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersCompany",
                    value = "用户公司", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersWork",
                    value = "用户工作岗位", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersAddress",
                    value = "公司名称", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersPay",
                    value = "薪资", dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "userId",
                    value = "用户id", required = true, dataTypeClass = Integer.class)
    })
    public RespBean updateUsersInformation(@Valid FixedInformationBean fixedInformationBean, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = bindingResult.getFieldErrors();
            logger.error("更新失败");
            for (FieldError error : errors) {
                logger.error("错误的字段名：" + error.getField());
                logger.error("错误信息：" + error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
            return RespBean.error(map);
        }
        boolean judgment = iFixedInformationService.updateUsersInformation(fixedInformationBean);
        if (judgment) {
            logger.info("更新成功");
            return RespBean.ok("更新成功");
        } else {
            logger.error("更新失败");
            return RespBean.error("更新失败");
        }
    }

}
