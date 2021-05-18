package studio.banner.forumwebsite.controller.background;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
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
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.bean.UsersInformationBean;
import studio.banner.forumwebsite.service.IUsersInformationService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: Ljx
 * @Date: 2021/5/15 16:15
 */
@RestController
@Api(tags = "所有用户信息接口", value = "UsersInformationController")
public class UsersInformationController {
    private static final Logger logger = LoggerFactory.getLogger(UsersInformationController.class);
    @Autowired
    private IUsersInformationService iUsersInformationService;

    @ApiOperation(value = "增加用户信息", notes = "用户姓名不能为空",httpMethod = "POST")
    @PostMapping("/insertUsersInformation")
    public RespBean insertUsersInformation(UsersInformationBean  usersInformationBean){
        for (UsersInformationBean list:  iUsersInformationService.selectUsersInformation()) {
            if (usersInformationBean.getUserId().equals(list.getUserId())){
                logger.error("添加失败");
                return RespBean.error("用户信息已存在");
            }
        }
        if (iUsersInformationService.insertUsersInformation(usersInformationBean)){
            logger.info("添加用户信息成功");
            return RespBean.ok("添加用户信息成功");
        }else {
            logger.error("添加用户信息失败");
        }
        return RespBean.error("添加用户信息失败");
    }

    @ApiOperation(value = "删除用户信息", notes = "id与userID相等时删除",httpMethod = "DELETE")
    @DeleteMapping("/deleteUsersInformation")
    public RespBean deleteUsersInformation(Integer id){
        for (UsersInformationBean list:  iUsersInformationService.selectUsersInformation()) {
            if (id.equals(list.getUserId())){
                iUsersInformationService.deleteUsersInformation(id);
                logger.info("删除成功");
                return RespBean.ok("用户信息删除成功");
            }
        }
        logger.error("删除失败");
        return RespBean.error("找不到无用户信息");
    }

    @PutMapping("/updateUsersInformation")
    @ApiOperation(value = "用户信息更改",notes = "用户不能为空",httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "usersName",
            value = "用户姓名",required = true,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "usersDirection",
            value = "用户方向",required = false,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "usersPhone",
            value = "用户手机号",required = false,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "usersQQ",
            value = "用户QQ号",required = false,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "usersWeChat",
            value = "用户微信号",required = false,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "usersCompany",
            value = "用户公司",required = false,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "usersWork",
            value = "用户工作岗位",required = false,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "usersAddress",
            value = "公司名称",required = false,dataType = "String"),
            @ApiImplicitParam(paramType = "query",name = "usersPay",
            value = "薪资",required = false,dataType = "int"),
            @ApiImplicitParam(paramType = "query",name = "userId",
            value = "用户id",required = false,dataType = "int")
    })
    public RespBean updateUsersInformation(@Valid UsersInformationBean usersInformationBean, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String,Object> map = new HashMap<>();
            List<FieldError> errors = bindingResult.getFieldErrors();
            logger.error("更新失败");
            for (FieldError error: errors){
                logger.error("错误的字段名：" + error.getField());
                logger.error("错误信息：" + error.getDefaultMessage());
                map.put(error.getField(), error.getDefaultMessage());
            }
            return RespBean.error(map);
        }
        boolean judgment = iUsersInformationService.updateUsersInformation(usersInformationBean);
        if (judgment){
            logger.info("更新成功");
            return RespBean.ok("更新成功");
        }else {
            logger.error("更新失败");
            return RespBean.error("更新失败");
        }
    }








    @ApiOperation(value = "分页查询",notes = "页数不为空",httpMethod = "GET")
    @GetMapping("/pageUsersInformation")
    public RespBean pageUsersInformation(Integer page){
        if (page !=null){
            IPage<UsersInformationBean> usersInformationBeanIPage = iUsersInformationService.selectUsersInformationBeanPage(page);
            List<UsersInformationBean> list =usersInformationBeanIPage.getRecords();
            if (list.size() != 0){
                logger.info("分页查询成功");
                return RespBean.ok("分页查询成功",list);
            }else {
                logger.error("分页查询失败");
                return RespBean.error("无当前页数");
            }
        }
        logger.error("分页查询失败");
        return RespBean.error("分页查询失败");
    }
}
