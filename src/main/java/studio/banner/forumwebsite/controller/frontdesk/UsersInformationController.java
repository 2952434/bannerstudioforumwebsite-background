package studio.banner.forumwebsite.controller.frontdesk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import studio.banner.forumwebsite.bean.AuthUser;
import studio.banner.forumwebsite.bean.FixedInformationBean;
import studio.banner.forumwebsite.bean.RespBean;
import studio.banner.forumwebsite.service.IFixedInformationService;

import javax.servlet.http.HttpServletRequest;
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
    private RestTemplate restTemplate;
    @Autowired
    private IFixedInformationService iFixedInformationService;

    @PutMapping("/updateUsersInformation")
    @ApiOperation(value = "用户信息更改", notes = "用户不能为空", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id",
                    value = "主键id", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(paramType = "query", name = "usersDirection",
                    value = "用户方向", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersName",
                    value = "用户姓名", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "userGrade",
                    value = "用户年级", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersCompany",
                    value = "用户公司", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersWork",
                    value = "用户工作岗位", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "usersAddress",
                    value = "公司地址", dataTypeClass = String.class),
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

    @GetMapping("/selectUsersInformationById/{userId}")
    @ApiOperation(value = "根据用户id查询用户信息",httpMethod = "GET")
    @ApiImplicitParam(paramType = "query", name = "userId",
            value = "用户id", required = true, dataTypeClass = Integer.class)
    public RespBean selectUsersInformationById(@PathVariable Integer userId) {
        FixedInformationBean fixedInformationBean = iFixedInformationService.selectUsersInformationById(userId);
        if (fixedInformationBean!=null){
            return RespBean.ok("查询成功",fixedInformationBean);
        }
        return RespBean.error("无该用户信息");
    }

    @GetMapping("/selectUserGrade")
    @ApiOperation(value = "查询所有年级",httpMethod = "GET")
    public RespBean selectGradeGroupBy(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", header);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RespBean> entity = restTemplate.exchange("https://oauth.bannerstudio.club/admin/selectGradeGroupBy/", HttpMethod.GET, httpEntity, RespBean.class);
//        ResponseEntity<RespBean> entity = restTemplate.exchange("http://192.144.233.202:8090/admin/selectGradeGroupBy/", HttpMethod.GET, httpEntity, RespBean.class);
        return entity.getBody();
    }

    @GetMapping("/selectDirectionGroupBy/{grade}")
    @ApiOperation(value = "根据年级查询所有方向",httpMethod = "GET")
    public RespBean selectDirectionGroupBy(HttpServletRequest request,@PathVariable String grade){
        String header = request.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", header);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RespBean> entity = restTemplate.exchange("https://oauth.bannerstudio.club/admin/selectDirectionGroupBy"+grade, HttpMethod.GET, httpEntity, RespBean.class);
//        ResponseEntity<RespBean> entity = restTemplate.exchange("http://192.144.233.202:8090/admin/selectDirectionGroupBy"+grade, HttpMethod.GET, httpEntity, RespBean.class);
        return entity.getBody();
    }

    @GetMapping("/selectUserIdAndMemberName/{direction}/{grade}")
    @ApiOperation(value = "根据年级和方向查询成员",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(type = "query", name = "direction",
                    value = "方向", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(type = "query", name = "grade",
                    value = "年级", required = true, dataTypeClass = String.class)
    })
    public RespBean selectUserIdAndMemberName(HttpServletRequest request,@PathVariable String direction,@PathVariable String grade){
        Map<String,String> map = new HashMap<>();
        map.put("direction",direction);
        map.put("grade",grade);
        String header = request.getHeader("Authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", header);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RespBean> entity = restTemplate.exchange("https://oauth.bannerstudio.club/admin/selectUserIdAndMemberName/{direction}/{grade}", HttpMethod.GET, httpEntity, RespBean.class,map);
//        ResponseEntity<RespBean> entity = restTemplate.exchange("http://192.144.233.202:8090/admin/selectUserIdAndMemberName/{direction}/{grade}", HttpMethod.GET, httpEntity, RespBean.class,map);
        return entity.getBody();
    }


}
